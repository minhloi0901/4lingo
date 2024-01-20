from flask import Blueprint, request, jsonify
from controllers import Communities_controller

community_router = Blueprint('communities', __name__)

# Routes
@community_router.route('/', methods=['GET'])
def get_all_communities():
    return Communities_controller.get_all_communities()


@community_router.route('/id', methods=['GET'])
def get_community_by_id():
    return Communities_controller.get_community_by_id()


@community_router.route('/', methods=['PATCH'])
def update_community_by_id():
    return Communities_controller.update_community_by_id()


@community_router.route('/', methods=['DELETE'])
def delete_community_by_id(id):
    return Communities_controller.delete_community_by_id()

@community_router.route('/', methods=['POST'])
def create_new_community():
    return Communities_controller.create_new_community()










