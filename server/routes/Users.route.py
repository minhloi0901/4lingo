from flask import Blueprint, jsonify, request
from errors.errors import *
from User.controller import UserController

user_bp = Blueprint('user', __name__)
user_controller = UserController()

@user_bp.route('/users', methods=['GET'])
def get_users():
    users = user_controller.get_all_users()
    if not users:
        return jsonify(NOT_FOUND_404[0]), NOT_FOUND_404[1]
    return jsonify(users), 200

@user_bp.route('/users/<int:user_id>', methods=['GET'])
def get_user(user_id):
    user = user_controller.get_user_by_id(user_id)
    if not user:
        return jsonify(NOT_FOUND_404[0]), NOT_FOUND_404[1]
    return jsonify(user), 200

@user_bp.route('/users', methods=['POST'])
def create_user():
    data = request.get_json()
    if not data:
        return jsonify(NO_INPUT_400[0]), NO_INPUT_400[1]

    username = data.get('username')
    # Validate input
    if not username:
        return jsonify(INVALID_INPUT_422[0]), INVALID_INPUT_422[1]

    # Create user
    new_user = user_controller.create_user(username)
    if not new_user:
        return jsonify(SERVER_ERROR_500[0]), SERVER_ERROR_500[1]
    return jsonify(new_user), 201
