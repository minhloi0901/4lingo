from flask import Blueprint, request
from controllers import Users_Achievements_controller

user_achievement = Blueprint('user_achievement', __name__)

@user_achievement.route('/users_achievements/', methods=['POST'])
def create_new_user_achievement():
    return Users_Achievements_controller.create_new_user_achievement()


@user_achievement.route('/users_achievements/', methods=['GET'])
def get_all_users_achievements():
    return Users_Achievements_controller.get_all_users_achievements()


@user_achievement.route('/users_achievements/<user_id>', methods=['GET'])
def get_user_achievement_by_id(user_id):
    return Users_Achievements_controller.get_user_achievement_by_id(user_id)