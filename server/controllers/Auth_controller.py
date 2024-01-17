from flask import jsonify, request, make_response
from models import Users_model
import bcrypt 
from werkzeug.security import check_password_hash
from middlewares.Auth_middleware import generate_token

salt_rounds = 10

def signup():
    data = request.json
    password = data.get('password')

    # Hash the password
    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
    data['password'] = hashed_password

    # Create new user with hashed password
    try:
        new_user = Users_model(data)
        new_user.save()
        response = make_response(login(request))
        response.status_code = 201
        return response
    except Exception as e:
        if 'ER_DUP_ENTRY' in str(e):
            return jsonify({'message': 'User has already existed'}), 409
        return jsonify({'message': 'Errors occur when creating new user'}), 500
    
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