# Users.controller.py
from flask import jsonify, request

from errors.Errors import ALREADY_EXIST, NO_INPUT_400, INVALID_INPUT_422
from models.Users_model import User
from middlewares.Auth_validator import validate_password, validate_username, validate_email, validate_phone_number
from middlewares.Auth_middleware import hash_password, get_id_from_token
import bcrypt


salt_rounds = 8

def create_new_user(username, password, role, email, phone_number=None):
    # Validate username
    is_valid_username, error_message_username = validate_username(username)
    if not is_valid_username:
        return False, error_message_username
    
    # Validate email
    is_valid_email, error_message_email = validate_email(email)
    if not is_valid_email:
        return False, error_message_email
    
    # Validate phone number
    is_valid_phone_number, error_message_phone_number = validate_phone_number(phone_number)
    if not is_valid_phone_number:
        return False, error_message_phone_number
    
    # Validate password
    is_valid_password, error_message_password = validate_password(password)
    if not is_valid_password:
        return False, error_message_password
    
    # Hash password
    hashed_password = hash_password(password)
    # Create a new user if all checks pass
    new_user = User.create_new_user(username=username, password=hashed_password, role=role, email=email, phone_number=phone_number)
    
    return True, 'User created successfully!'

def get_user_by_id(user_id):
    filter_criteria = User.id == user_id
    user = User.find_one_user_by_filter(filter_criteria)
    if user:
        user_data = {
            'id': user.id,
            'username': user.username,
            'password': user.password,  
            'score': user.score,
            'rating': user.rating,
            'role': user.role.value,
            'phone_number': user.phone_number,
            'email': user.email
        }
        return jsonify(user_data)
    return jsonify({'message': 'User not found'})

def get_user_by_name(user_name):
    filter_criteria = User.username == user_name
    user = User.find_one_user_by_filter(filter_criteria)
    if user:
        user_data = {
            'id': user.id,
            'username': user.username,
            'password': user.password,  
            'score': user.score,
            'rating': user.rating,
            'role': user.role.value,
            'phone_number': user.phone_number,
            'email': user.email
        }
        return jsonify(user_data)
    return jsonify({'message': 'User not found'})

def update_user_by_id(user_id, update_data):
    filter_criteria = User.id == user_id
    User.update_user_by_filter(filter_criteria, update_data)
    return jsonify({'message': 'User updated successfully!'})

def delete_user_by_id(user_id):
    filter_criteria = User.id == user_id
    User.delete_users_by_filter(filter_criteria)
    return jsonify({'message': 'User deleted successfully!'})

def get_all_users():
    filter_criteria = True  
    users = User.find_all_users_by_filter(filter_criteria)
    user_list = []

    for user in users:
        user_data = {
            'id': user.id,
            'username': user.username,
            'password': user.password,  
            'score': user.score,
            'rating': user.rating,
            'role': user.role.value,
            'phone_number': user.phone_number,
            'email': user.email
        }
        user_list.append(user_data)

    return jsonify(user_list)

def update_password_by_email(email, password):
    filter_criteria = User.email == email
    # Validate password
    is_valid_password, error_message_password = validate_password(password)
    if not is_valid_password:
        return False, error_message_password
    # Hash password
    hashed_password = hash_password(password)
    # Update password
    update_data = {
        'password': hashed_password
    }
    User.update_user_by_filter(filter_criteria, update_data)
    return jsonify({'message': 'Password updated successfully!'})

def get_user_by_token(user_token):
    is_valid_token, error_message = get_id_from_token(user_token)
    if not is_valid_token:
        return jsonify({'message': error_message})
    
    user_id = error_message
    filter_criteria = User.id == user_id
    user = User.find_one_user_by_filter(filter_criteria)
    if user:
        user_data = {
            'id': user.id,
            'username': user.username,
            'password': user.password,  
            'score': user.score,
            'rating': user.rating,
            'role': user.role.value,
            'phone_number': user.phone_number,
            'email': user.email
        }
        return jsonify(user_data)
    else:
        return jsonify({'message': 'User not found'})

def update_user(user_token, update_data):
    # Validate the user token and get the user ID
    is_valid_token, user_id = get_id_from_token(user_token)
    if not is_valid_token:
        return jsonify({'message': user_id}), 400

    # Extract the fields from the update data
    new_email = update_data.get('email', None)
    new_phone_number = update_data.get('phone_number', None)
    new_username = update_data.get('username', None)

    # Check if the new email already exists
    if new_email:
        existing_email_user = User.find_one_user_by_filter(User.email == new_email)
        if existing_email_user and existing_email_user.id != user_id:
            return jsonify({'message': 'Email already exists'}), 401

    # Check if the new phone number already exists
    if new_phone_number:
        existing_phone_user = User.find_one_user_by_filter(User.phone_number == new_phone_number)
        if existing_phone_user and existing_phone_user.id != user_id:
            return jsonify({'message': 'Phone number already exists'}), 402

    # Check if the new username already exists
    if new_username:
        existing_username_user = User.find_one_user_by_filter(User.username == new_username)
        if existing_username_user and existing_username_user.id != user_id:
            return jsonify({'message': 'Username already exists'}), 403

    # Update the user information
    update_data = {
        'email': new_email,
        'phone_number': new_phone_number,
        'username': new_username
    }

    filter_criteria = User.id == user_id
    User.update_user_by_filter(filter_criteria, update_data)

    return jsonify({'message': 'User updated successfully!'}), 200

def update_password(user_token, update_data):
    # Extract data from the update_data dictionary
    old_password = update_data.get('old_password')
    new_password = update_data.get('new_password')

    # Validate the new password
    is_valid_password, error_message_password = validate_password(new_password)
    if not is_valid_password:
        return jsonify({'message': error_message_password}), 400

    # Get user_id from the token
    is_valid_token, user_id = get_id_from_token(user_token)
    if not is_valid_token:
        return jsonify({'message': user_id}), 401  # user_id contains the error message

    # Find the user by user_id
    filter_criteria = User.id == user_id
    user = User.find_one_user_by_filter(filter_criteria)

    if user:
        # Check if the old password matches the stored password
        if bcrypt.checkpw(old_password.encode('utf-8'), user.password.encode('utf-8')):
            # Hash the new password
            hashed_new_password = hash_password(new_password)

            # Update the user's password
            update_data = {'password': hashed_new_password}
            User.update_user_by_filter(filter_criteria, update_data)

            return jsonify({'message': 'Password updated successfully!'}), 200
        else:
            return jsonify({'message': 'Old password is incorrect.'}), 402
    else:
        return jsonify({'message': 'User not found'}), 403

def update_user_lesson(): 
    try:
        data = request.json
        user_token = data.get("token")
        additional_score = data.get("total_score")
        lesson_type = data.get("type")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400

    is_valid_token, user_id_or_error = get_id_from_token(user_token)
    if not is_valid_token:
        return jsonify({'message': user_id_or_error}), 401
    
    # Find the user by user_id
    filter_criteria = User.id == user_id_or_error
    user = User.find_one_user_by_filter(filter_criteria)

    if user:
        # Update the user's score and lesson type count
        new_score = user.score + additional_score
        lesson_type_field = f'lesson_type_{lesson_type}'
        if hasattr(user, lesson_type_field):
            current_count = getattr(user, lesson_type_field)
            setattr(user, lesson_type_field, current_count + 1)
        else:
            return jsonify({'message': f'Invalid lesson type: {lesson_type}'}), 400

        update_data = {
            'score': new_score,
            # Update the specific lesson type field
            lesson_type_field: getattr(user, lesson_type_field)
        }
        User.update_user_by_filter(filter_criteria, update_data)

        return jsonify({'message': 'User lesson and score updated successfully!'}), 200
    else:
        return jsonify({'message': 'User not found'}), 403
    