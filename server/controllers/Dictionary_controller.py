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
import requests

salt_rounds = 8

def search(): 
    word = request.args.get('word', '')
    from flask import jsonify, request

def search():
    word = request.args.get('word', '')

    if not word:
        return jsonify({"error": "No word provided"}), 400

    api_url = f"https://api.dictionaryapi.dev/api/v2/entries/en/{word}"
    response = requests.get(api_url)

    if response.status_code == 200:
        return jsonify(response.json())
    else:
        return jsonify({"error": "Word not found or error in external API"}), response.status_code