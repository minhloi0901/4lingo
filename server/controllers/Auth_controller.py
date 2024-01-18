from flask import jsonify, request, make_response
from models.Users_model import User
from controllers.Users_controller import create_new_user
from middlewares.Auth_middleware import generate_token

from flask import jsonify, request, make_response
from werkzeug.security import generate_password_hash
from models.Users_model import User
from middlewares.Auth_middleware import generate_token
from sqlalchemy.exc import IntegrityError
import bcrypt

salt_rounds = 8

def signup():
    data = request.json
    username = data.get('username')
    password = data.get('password')
    role = data.get('role')
    phone_number = data.get('phone_number')
    email = data.get('email')
    
    success, response = create_new_user(username, password, role, email, phone_number)
    if success:
        return jsonify({'message': 'User created successfully.'})
    else:
        return jsonify({'message': response})
    
def login():
    data = request.json
    username = data.get('username')
    password = data.get('password')

    filter_criteria = User.username == username
    user = User.find_one_user_by_filter(filter_criteria)

    if not user:
        return jsonify({'message': 'User not found'})

    stored_hashed_password = user.password
    if bcrypt.checkpw(password.encode('utf-8'), stored_hashed_password.encode('utf-8')):
        # token = generate_token(user.id)
        # return jsonify({'message': 'Login success!', 'token': token})
        return jsonify({'message': 'Login success!'})
    else:
        return jsonify({'message': 'Password incorrect'})
    
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