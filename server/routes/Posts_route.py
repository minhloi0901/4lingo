from flask import Blueprint, request, jsonify
from controllers.Posts_controller import Posts_controller

post_router = Blueprint('posts', __name__)

@post_router.route('/', methods=['GET'])
def get_all_posts():
    return Posts_controller.get_all_posts()

@post_router.route('/', methods=['POST'])
def create_new_post():
    data = request.get_json()
    return Posts_controller.create_new_post(data)


@post_router.route('/community/', methods=['GET'])
def get_all_posts_by_community_id():
    data = request.get_json()
    return Posts_controller.get_all_posts_by_community_id(data)














