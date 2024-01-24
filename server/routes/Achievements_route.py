from flask import Blueprint, request
from controllers import Achievements_controller

achievement_router = Blueprint('achievements', __name__)

@achievement_router.route('/get_user_achievements', methods=['POST'])
def get_user_achievements():
    return Achievements_controller.get_user_achievements_by_token()

@achievement_router.route('/add_user_achievements', methods=['POST'])
def add_user_achievements():
    return Achievements_controller.add_user_achievement_by_token()
