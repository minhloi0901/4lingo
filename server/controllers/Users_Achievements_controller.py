from errors.Errors import ALREADY_EXIST, NO_INPUT_400, INVALID_INPUT_422
from models.Achievements_model import Achievement
from models.Users_model import User
from models.Users_Achievements_model import Users_Achievements
from database.db import db
from flask import jsonify, request
from database.db import db

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
        return jsonify({'message': NO_INPUT_400}), 400
    
    new_user_achievement = Users_Achievements.create_new_user_achievement(user_id, achievement_id, date_achieved)
    if not new_user_achievement:
        return jsonify({'message': 'error'}), 400

    return jsonify({'message': 'New user_achievement created successfully!'}), 201


def get_user_achievement_by_id():
    try:
        data = request.json
        id = data.get("user_id")
        token = data.get("token")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    if not id:
        return jsonify({'message': NO_INPUT_400}), 400

    filter_criteria = (Users_Achievements.user_id == id)
    user_achievement = Users_Achievements.find_one_user_achievement_by_filter(filter_criteria)
    
    result_dict = []
    for i in user_achievement:
        new_dict = i.__dict__
        result_dict.append(new_dict)
    
    # Return result
    return jsonify(result_dict), 200
    
    
def get_all_users_achievements():
    user_achievement = Users_Achievements.find_all_users_achievements_by_filter(True)
    
    result_dict = []
    for i in user_achievement:
        new_dict = i.__dict__
        result_dict.append(new_dict)
    
    # Return result
    return jsonify(result_dict), 200
