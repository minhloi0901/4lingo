from flask import Blueprint, request, jsonify
from ..controllers.Communities_controller import *

community_router = Blueprint('communities', __name__)

# Routes
@community_router.route('/', methods=['GET'])
def get_all_communities():
    return get_all_communities(Community)


@community_router.route('/', methods=['GET'])
def get_community_by_id(id):
    return get_community_by_id(Community, id)


@community_router.route('/', methods=['PATCH'])
def update_community_by_id():
    return update_community_by_id(Community, request.json)


@community_router.route('/', methods=['DELETE'])
def delete_community_by_id(id):
    return delete_community_by_id(Community, id)









