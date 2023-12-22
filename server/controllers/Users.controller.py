# Users.controller.py
from flask import jsonify
from errors.Errors import ALREADY_EXIST, NO_INPUT_400, INVALID_INPUT_422
from Models.Users.model import db, User

def create_user(username, email, phone_number=None):
    # Check if username, email, and phone number are provided
    if not username or not email:
        return NO_INPUT_400

    # Check for valid email format (add more detailed validation if needed)
    if '@' not in email or '.' not in email:
        return INVALID_INPUT_422

    # Check if phone number is provided and not empty
    if phone_number and phone_number.strip() != '':
        existing_phone_user = User.find_user_by_phone_number(phone_number)
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
