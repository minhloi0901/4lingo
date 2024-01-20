from flask import Blueprint, request
from controllers import Users_Achievements_controller

user_achievement_router = Blueprint('users_achievements', __name__)

@user_achievement_router.route('/', methods=['POST'])
def create_new_user_achievement():
    return Users_Achievements_controller.create_new_user_achievement()


@user_achievement_router.route('/', methods=['GET'])
def get_user_achievement_by_id():
    return Users_Achievements_controller.get_all_users_achievements_by_user_id()