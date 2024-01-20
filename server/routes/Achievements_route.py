from flask import Blueprint, request
from controllers import Achievements_controller

achievement_router = Blueprint('achievements', __name__)

@achievement_router.route('/', methods=['POST'])
def create_new_achievement():
    return Achievements_controller.create_new_achievement()


@achievement_router.route('/delete_by_id/', methods=['DELETE'])
def delete_achievement_by_id():
    return Achievements_controller.delete_achievement_by_id()


@achievement_router.route('/delete_by_name', methods=['DELETE'])
def delete_achievement_by_name():
    return Achievements_controller.delete_achievement_by_name()


@achievement_router.route('/', methods=['GET'])
def get_all_achievements():
    return Achievements_controller.get_all_achievements()


