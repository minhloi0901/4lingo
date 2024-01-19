from errors.Errors import ALREADY_EXIST, NO_INPUT_400, INVALID_INPUT_422
from models.Achievements_model import Achievement
from models.Users_model import User
from models.Users_Achievements_model import Users_Achievements
from database.db import db
from flask import jsonify, request
from database.db import db
from middlewares.Auth_middleware import get_id_from_token

Session = db['Session']
session = Session()
Base = db['Base']

salt_rounds = 8

def create_new_user_achievement():
    try:
        data = request.json
        user_id = data.get("user_id")
        achievement_id = data.get("achievement_id")
        date_achieved = data.get("date_achieved")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400

    if not user_id or not achievement_id or not date_achieved:
        return jsonify({'message': "No input data provided or not enough input data"}), 400
    
    new_user_achievement, msg = Users_Achievements.create_new_user_achievement(user_id, achievement_id, date_achieved)
    
    if not new_user_achievement:
        return jsonify({'message': msg}), 400

    return jsonify({'message': 'New user_achievement created successfully!'}), 201


def get_all_users_achievements_by_user_id():
    try:
        data = request.json
        user_token = data.get("token")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    is_valid_token, error_message = get_id_from_token(user_token)
    
    if not is_valid_token:
        return jsonify({'message': error_message}), 401
    
    user_id = error_message
    filter_criteria = (Users_Achievements.user_id == user_id)
    users_achievements = Users_Achievements.find_all_users_achievements_by_filter(filter_criteria)
    
    result_list = []
    for entry in users_achievements:
        new_dict = {
            "user_id": entry.user_id,
            "achievement_id": entry.achievement_id,
            "date_achieved": entry.date_achieved
        }
        result_list.append(new_dict)
    # Return result
    return jsonify(result_list), 200
    
    
def get_all_users_achievements():
    user_achievement = Users_Achievements.find_all_users_achievements_by_filter(True)
    
    result_dict = []
    for i in user_achievement:
        new_dict = i.__dict__
        result_dict.append(new_dict)
    
    # Return result
    return jsonify(result_dict), 200
