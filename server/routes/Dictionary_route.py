from flask import Blueprint, request
from controllers import Dictionary_controller

dict_router = Blueprint('dictionary', __name__)

@dict_router.route("search", methods = ["GET"])
def search(): 
    return Dictionary_controller.search()