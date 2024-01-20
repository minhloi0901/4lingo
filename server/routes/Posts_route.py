from flask import Blueprint, request, jsonify
from ..controllers import Posts_controller

post_router = Blueprint('posts', __name__)

@post_router.route('/post', methods=['GET'])
def get_all_posts():
    return Posts_controller.get_all_posts()


@post_router.route('/post/<community_id>/<id>' methods=['GET'])
def get_post_by_id(community_id, id):
    return Posts_controller.get_post(community_id, id)


@post_router.route('/post', methods=['PATCH'])
def update_post_by_id():
    data = request.get_json()
    return Posts_controller.update_post(data)


def create_new_post():
    data = request.get_json()
    return Posts_controller.create_new_post(data)












