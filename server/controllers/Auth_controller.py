from flask import jsonify, request
from models.Users_model import User
from controllers.Users_controller import create_new_user, update_password_by_email
from werkzeug.security import generate_password_hash
from models.Users_model import User
from middlewares.Auth_middleware import generate_token
from sqlalchemy.exc import IntegrityError
import bcrypt

from email.message import EmailMessage
from itsdangerous import URLSafeTimedSerializer
import ssl
import smtplib

import secrets
from datetime import datetime, timedelta

from config.config import config
secret_key = config['SECRET_KEY']
secret_salt = config['SECRET_SALT']
expires_sec = 300
serializer = URLSafeTimedSerializer(secret_key)
salt_rounds = 8

email_sender = '4Lingo21TN@gmail.com'  
email_password = 'wwgp izbk qpyw rjpm'  

def signup():
    data = request.json
    print(data)
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
        token = generate_token(user.id)
        return jsonify({'message': 'Login success!',
                        'token': token})
    else:
        return jsonify({'message': 'Password incorrect'})

token_storage = {}
expiration_storage = {}

def reset_password():
    data = request.json
    email = data.get('email')

    filter_criteria = User.email == email
    valid_email = User.find_one_user_by_filter(filter_criteria)
    if not valid_email:
        return jsonify({'message': 'Email not found'})
    else:
        verify_code, expiration = send_email(email)
        return jsonify({
            'message': 'Reset password request has been sent to your email',
            'token': verify_code,
            'expiration': expiration.isoformat() if expiration else None
        })

def generate_random_token():
    return secrets.randbelow(1000000)

def get_expiration_time():
    return datetime.now() + timedelta(minutes=1)

def send_email(email):
    em = EmailMessage()
    em['From'] = email_sender  
    em['To'] = email
    em['Subject'] = 'Reset Password Request'
    expiration_storage = get_expiration_time()
    token_storage = generate_random_token()
    body = f"""
    Your 6-digit verification code is:
    {token_storage}
    This code will expire in {expiration_storage}.
    """

    em.set_content(body)
    
    context = ssl.create_default_context()

    with smtplib.SMTP_SSL('smtp.gmail.com', 465, context=context) as smtp:
        smtp.login(email_sender, email_password)
        smtp.sendmail(email_sender, email, em.as_string())

    return token_storage, expiration_storage

def change_password():
    data = request.json
    email = data.get('email')
    password = data.get('password')

    filter_criteria = User.email == email
    user = User.find_one_user_by_filter(filter_criteria)
    if not user:
        return jsonify({'message': 'Email not found'})
    else:
        update_password_by_email(email, password)
        return jsonify({'message': 'Password changed successfully!'})
    
