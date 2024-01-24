from models.Achievements_model import Achievement
from database.db import db
from flask import jsonify, request
from database.db import db
from middlewares.Auth_middleware import get_id_from_token

Session = db['Session']
session = Session()
Base = db['Base']

salt_rounds = 8

def get_user_achievements_by_token(): 
    try:
        data = request.json
        user_token = data.get("token")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    is_valid_token, error_message = get_id_from_token(user_token)
    if not is_valid_token:
        return jsonify({'message': error_message}), 401

    # Query all achievements from the table
    all_achievements = Achievement.find_all_achievements_by_filter(True)

    # Convert the achievements to a list of dictionaries
    achievements_list = []
    for achievement in all_achievements:
        achievements_list.append({
            'id': achievement.id,
            'name': achievement.name,
            'content': achievement.content,
            'criteria': achievement.criteria
        })

    return jsonify({'achievements': achievements_list}), 200

def add_user_achievement_by_token(): 
    try:
        data = request.json
        user_token = data.get("token")
        name = data.get("name")
        content = data.get("content")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    is_valid_token, error_message = get_id_from_token(user_token)
    if not is_valid_token:
        return error_message, 401

    user_id = error_message

    Achievement.create_new_achievement(name, content, f'user_id={user_id}')

    return jsonify({'message': 'New achievement added successfully!'}), 201
