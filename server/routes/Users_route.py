from flask import Blueprint, request
from controllers import Users_controller

user_router = Blueprint('users', __name__)

@user_router.route("/", methods=["GET"])
def get_all_users():
    return Users_controller.get_all_users()

@user_router.route("/<user_name>", methods=["GET"])
def get_user_by_name(user_name):
    return Users_controller.get_user_by_name(user_name)

@user_router.route("/", methods=["PATCH"])
def update_user():
    return Users_controller.update_user_by_id(request.json)

@user_router.route("/", methods=["DELETE"])
def delete_user():
    return Users_controller.delete_user_by_id()