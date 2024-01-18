from flask import Blueprint, jsonify, request
from controllers import Auth_controller

auth_router = Blueprint('auth', __name__)

@auth_router.route("/")
def root():
    return ('this is 4lingo backend server auth route')

@auth_router.route("/login", methods=["POST"])
def login():
    return Auth_controller.login()

@auth_router.route("/signup", methods=["POST"])
def signup():
    return Auth_controller.signup()

@auth_router.route("/reset_password", methods=["POST", "GET"])
def reset_password():
   return Auth_controller.reset_password()

@auth_router.route("/reset_password/<token>", methods=["POST", "GET"])
def reset_password_with_token(token):
   return Auth_controller.reset_password_with_token(token)

@auth_router.route("/change_password", methods=["POST"])
def change_password():
   return Auth_controller.change_password()

@auth_router.route("/logout", methods=["POST"])
def logout():
    return jsonify({'message': 'Logout successfully'})
