# Users.controller.py
from flask import jsonify

from errors.Errors import ALREADY_EXIST, NO_INPUT_400, INVALID_INPUT_422
from models.Users_model import User
from database.db import db

def create_user(username, email, phone_number=None):
    # Check if username, email, and phone number are provided
    if not username or not email:
        return NO_INPUT_400

    # Check for valid email format (add more detailed validation if needed)
    if '@' not in email or '.' not in email:
        return INVALID_INPUT_422

    # Check if phone number is provided and not empty
    if phone_number and phone_number.strip() != '':
        filter_by_phone_number = User.phone_number == phone_number
        existing_phone_user = User.find_user_by_phone_number(filter_by_phone_number)
        if existing_phone_user:
            return ALREADY_EXIST

    # Check for existing username or email
    filter_by_username_or_email = (User.username == username) | (User.email == email)
    existing_user = User.find_one_user_by_filter(filter_by_username_or_email)
    if existing_user:
        return ALREADY_EXIST

    # Create a new user if all checks pass
    new_user = User(username=username, email=email, phone_number=phone_number)
    db.session.add(new_user)
    db.session.commit()

    return jsonify({'message': 'User created successfully!'}), 201

def add_new_user_test():
    # Provide user information to create a new user (for testing purposes)
    test_username = "TestUser"
    test_email = "testuser@example.com"
    test_phone_number = "1234567890"  # Optional, can be None or empty string

    # Call create_user function to add a new user
    response = create_user(test_username, test_email, test_phone_number)

    # Print or handle the response
    print(response)

add_new_user_test()