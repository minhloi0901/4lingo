from flask import jsonify, request, make_response
from werkzeug.security import generate_password_hash, check_password_hash
from models.Users_model import User
from middlewares.Auth_middleware import generate_token
from sqlalchemy.exc import IntegrityError

salt_rounds = 10

from flask import jsonify, request, make_response
from werkzeug.security import generate_password_hash
from models.Users_model import User
from middlewares.Auth_middleware import generate_token
from sqlalchemy.exc import IntegrityError

def signup():
    try:
        data = request.json
        username = data.get('username')
        password = data.get('password')
        role = data.get('role')  # Make sure 'role' is in your JSON data
        email = data.get('email')
        phone_number = data.get('phone_number')

        # Hash the password using Flask's built-in utility
        hashed_password = generate_password_hash(password, method='pbkdf2:sha256')
        data['password'] = hashed_password

        # Create new user with hashed password
        new_user = User.create_new_user(
            username=username,
            password=password,
            role=role,
            email=email,
            phone_number=phone_number
        )

        response = make_response(jsonify({'message': 'User created successfully'}))
        response.status_code = 201
        return response
    except IntegrityError as e:
        if 'UNIQUE constraint failed' in str(e):
            response = jsonify({'message': 'User with this email or username already exists'})
            response.status_code = 409
            return response
        else:
            response = jsonify({'message': f'Error creating new user: {e}'})
            response.status_code = 500
            return response
    except Exception as e:
        response = jsonify({'message': f'Error creating new user: {e}'})
        response.status_code = 500
        return response
    
def login():
    data = request.json
    username = data.get('username')
    password = data.get('password')

    user = Users_model.find_one_by_username(username)

    if user and bcrypt.checkpw(password.encode('utf-8'), user['password'].encode('utf-8')):
        token = generate_token(user['id'])
        return jsonify({'message': 'Login successful', 'token': token}), 200
    else:
        return jsonify({'message': 'Invalid username or password'}), 401
    
def get_password():
    data = request.json
    email = data.get('email')

    user = Users_model.find_one_by_email(email)
    # if exist notify had sent reset request.
    if user:
        send_email()
        return jsonify({'message:' 'Reset password request has been sent to your email'}), 200
    
def send_email():
    pass