from flask import jsonify, request, make_response
from models.Users_model import User
from controllers.Users_controller import create_new_user
from werkzeug.security import check_password_hash, generate_password_hash
from middlewares.Auth_middleware import generate_token
from middlewares.Auth_validator import signup_scheme, login_scheme

salt_rounds = 10

from flask import jsonify, request, make_response
from werkzeug.security import generate_password_hash
from models.Users_model import User
from middlewares.Auth_middleware import generate_token
from sqlalchemy.exc import IntegrityError

def signup():
    data = request.json
    username = data.get('username')
    password = data.get('password')
    role = data.get('role')
    phone_number = data.get('phone_number')
    email = data.get('email')
    
    new_user = create_new_user(username, password, role, email, phone_number)
    if new_user:
        return jsonify({'message': 'User created successfully!'}), 201
    
def login():
    data = request.json
    username = data.get('username')
    password = data.get('password')

    filter_criteria = User.username == username
    user = User.find_one_user_by_filter(filter_criteria)

    hashed_password = generate_password_hash(password, method='pbkdf2:sha256')

    if user and check_password_hash(hashed_password, user.password):
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