import datetime
import jwt
import bcrypt
from config.config import config
from flask import jsonify

def generate_token(user_id):
    try:
        payload = {
            'exp': datetime.datetime.utcnow() + datetime.timedelta(days=0, minutes=30),
            'iat': datetime.datetime.utcnow(),
            'sub': user_id
        }
        
        token = jwt.encode(
            payload,
            config.get('SECRET_KEY'),
            algorithm='HS256'
        )

        return token  
    except Exception as e:
        return e 
    
def get_id_from_token(token):
    try:
        payload = jwt.decode(token, config.get('SECRET_KEY'), algorithms=['HS256'])
        return True, payload['sub']
    except jwt.ExpiredSignatureError:
        return False, jsonify({'message': 'Token expired. Please log in again.'})
    except jwt.InvalidTokenError:
        return False, jsonify({'message': 'Invalid token. Please log in again.'})
    
def hash_password(password):
    password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
    return password