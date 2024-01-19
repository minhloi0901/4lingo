from flask import Blueprint, request
from controllers import Achievements_controller

achievement_router = Blueprint('achievement', __name__)

@achievement_router.route('/', methods=['POST'])
def create_new_achievement():
    data = request.get_json()
    return Achievements_controller.create_new_achievement(data)


@achievement_router.route('/', methods=['GET'])
def get_achievement_by_id():
    data = request.get_json()
    return Achievements_controller.get_achievement_by_id(data)

@achievement_router.route('/', methods=['GET'])
def get_achievement_by_name():
    data = request.get_json()
    return Achievements_controller.get_achievement_by_name(data)

@achievement_router.route('/', methods=['PATCH'])
def update_achievement_by_id():
    data = request.get_json()
    return Achievements_controller.update_achievement_by_id(data)

@achievement_router.route('/', methods=['DELETE'])
def delete_achievement_by_id():
    data = request.get_json()
    return Achievements_controller.delete_achievement_by_id(data)


@achievement_router.route('/', methods=['GET'])
def get_all_achievements():
    return Achievements_controller.get_all_achievements()