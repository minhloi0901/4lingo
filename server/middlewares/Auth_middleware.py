import datetime
import jwt
import bcrypt
from flask import current_app as app

def generate_token(user_id):
    try:
        payload = {
            'exp': datetime.datetime.utcnow() + datetime.timedelta(days=0, seconds=5),
            'iat': datetime.datetime.utcnow(),
            'sub': user_id
        }
        return jwt.encode(
            payload,
            app.config.get('SECRET_KEY'),
            algorithm='HS256'
        )
    except Exception as e:
        return e
    
def hash_password(password):
    password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
    return password