from flask import Blueprint, request, jsonify
from controllers import Users_controller

profile_router = Blueprint('profile', __name__)

@profile_router.route("", methods=["POST"])
def get_user_by_token():
    data = request.json
    token = data.get('token')
    return Users_controller.get_user_by_token(token)

@profile_router.route("update", methods=["POST"])
def update_user():
    data = request.json
    token = data.get('token')
    return Users_controller.update_user(token, data)


