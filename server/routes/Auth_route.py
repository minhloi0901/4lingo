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

@auth_router.route("/get_password", methods=["POST", "GET"])
def get_password():
   return Auth_controller.get_password()

@auth_router.route("/logout", methods=["POST"])
def logout():
    return jsonify({'message': 'Logout successfully'})
