# Users.controller.py
from flask import jsonify

from errors.Errors import ALREADY_EXIST, NO_INPUT_400, INVALID_INPUT_422
from models.Users_model import User
from database.db import db

Session = db['Session']
session = Session()
Base = db['Base']

def create_new_user(username, password, role, email, phone_number=None):
    # Check if username, email, and phone number are provided
    if not username or not email:
        return NO_INPUT_400

    # Check for valid email format (add more detailed validation if needed)
    if '@' not in email or '.' not in email:
        return INVALID_INPUT_422

    # Check if phone number is provided and not empty
    if phone_number and phone_number.strip() != '':
        filter_by_phone_number = User.phone_number == phone_number
        existing_phone_user = User.find_one_user_by_filter(filter_by_phone_number)
        if existing_phone_user:
            return ALREADY_EXIST

    # Check for existing username or email
    filter_by_username_or_email = (User.username == username) | (User.email == email)
    existing_user = User.find_one_user_by_filter(filter_by_username_or_email)
    if existing_user:
        return ALREADY_EXIST

    # Create a new user if all checks pass
    new_user = User.create_new_user(username=username, password=password, role=role, email=email, phone_number=phone_number)

    return jsonify({'message': 'User created successfully!'})

def get_user_by_id(user_id):
    filter_criteria = User.id == user_id
    user = User.find_one_user_by_filter(filter_criteria)
    if user:
        return jsonify({'username': user.username})  
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
    filter_criteria = True  # No filter criteria to get all users
    users = User.find_all_users_by_filter(filter_criteria)
    user_list = []

    for user in users:
        user_data = {
            'id': user.id,
            'username': user.username,
            # 'password': user.password,  
            'score': user.score,
            'rating': user.rating,
            'role': user.role.value,
            'phone_number': user.phone_number,
            'email': user.email
        }
        user_list.append(user_data)

    return jsonify(user_list)

# def add_new_user_test():
#     # Provide user information to create a new user (for testing purposes)
#     test_username = "TestUser"
#     test_password = "TestPassword" 
#     test_role = "TEACHER"
#     test_email = "testuser@example.com"
#     test_phone_number = "123456290"  # Optional, can be None or empty string

#     # Call create_user function to add a new user
#     response = create_new_user(test_username, test_password, test_role, test_email, test_phone_number)

#     # Print or handle the response
#     print(response)

# add_new_user_test()