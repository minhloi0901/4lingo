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
        return jsonify({'message': ALREADY_EXIST}), 400

    return jsonify({'message': 'New user_achievement created successfully!'}), 201
