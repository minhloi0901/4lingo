# Users.controller.py
from flask import jsonify

from errors.Errors import ALREADY_EXIST, NO_INPUT_400, INVALID_INPUT_422
from models.Users_model import User
from database.db import db
import re 
import bcrypt

Session = db['Session']
session = Session()
Base = db['Base']

salt_rounds = 8

def create_new_user(username, password, role, email, phone_number=None):
    # Check if username, email are provided
    if not username or not email:
        return False, 'Username and email are required.'

    # Check for valid email format (add more detailed validation if needed)
    if '@' not in email or '.' not in email:
        return False, 'Invalid email format.'

    # Check if phone number is provided and not empty
    if phone_number and phone_number.strip() != '':
        filter_by_phone_number = User.phone_number == phone_number
        existing_phone_user = User.find_one_user_by_filter(filter_by_phone_number)
        if existing_phone_user:
            return False, 'Phone number already exists.'

    # Check for existing username
    filter_by_username = (User.username == username)
    existing_user = User.find_one_user_by_filter(filter_by_username)
    if existing_user:
        return False, 'Username already exists.'
    
    # Check for existing email
    filter_by_email = (User.email == email)
    existing_email_user = User.find_one_user_by_filter(filter_by_email)
    if existing_email_user:
        return False, 'Email already exists.'
    
    # Check for existing phone number
    filter_by_phone_number = (User.phone_number == phone_number)
    existing_phone_user = User.find_one_user_by_filter(filter_by_phone_number)
    if existing_phone_user:
        return False, 'Phone number already exists.'
    
    # Check username length
    if len(username) < 4:
        return False, 'Username must be at least 4 characters long.'
    
    # Check space username
    if ' ' in username or '\t' in username or '\n' in username:
        return False, 'Username must not contain any whitespace characters.'
   
    # Check password length
    if not (8 <= len(password) <= 32):
        print(len(password))
        return False, 'Password must be between 8 and 32 characters long.'
    
    # Check password contains at least one lowercase, uppercase, number, and special character
    if not (re.search(r'[a-z]', password) and 
        re.search(r'[A-Z]', password) and 
        re.search(r'\d', password) and 
        re.search(r'\W', password)):
        return False, 'Password must contain at least one lowercase, uppercase, number, and special character.'
    
    # Check space password
    if ' ' in password or '\t' in password or '\n' in password:
        return False, 'Password must not contain any whitespace characters.'
    
    # Hash password
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
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


def add_new_user_test():
    # Provide user information to create a new user (for testing purposes)
    test_username = "TestUser"
    test_password = "TestPassword" 
    test_role = "TEACHER"
    test_email = "testuser@example.com"
    test_phone_number = "123456290"  # Optional, can be None or empty string

#     # Call create_user function to add a new user
#     response = create_new_user(test_username, test_password, test_role, test_email, test_phone_number)

#     # Print or handle the response
#     print(response)

# add_new_user_test()