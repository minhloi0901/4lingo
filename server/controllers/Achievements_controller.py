from errors.Errors import ALREADY_EXIST, NO_INPUT_400, INVALID_INPUT_422
from models.Achievements_model import Achievement
from database.db import db
from flask import jsonify, request
from database.db import db

Session = db['Session']
session = Session()
Base = db['Base']

salt_rounds = 8


def create_new_achievement():
    try:
        data = request.json
        name = data.get("name")
        content = data.get("content")
        criteria = data.get("criteria")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400

    if not name or not content or not criteria:
        return jsonify({'message': "No input provided"}), 400
    
    existing_name = session.query(Achievement).filter(Achievement.name == name).first()
    if existing_name:
        return jsonify({'message': "Achievement name already exists"}), 400
    
    new_achievement = Achievement.create_new_achievement(name, content, criteria)
    if not new_achievement:
        return jsonify({'message': "Error creating achievement"}), 400

    return jsonify({'message': 'New achievement created successfully!'}), 201
    

def delete_achievement_by_id():
    try:
        data = request.json
        id = data.get("id")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400

    if not id:
        return jsonify({'message': NO_INPUT_400}), 400

    filter_criteria = (Achievement.id == id)
    deleted_count = Achievement.delete_achievements_by_filter(filter_criteria)
    
    # Return result
    if deleted_count == 0:
        return jsonify({'message': 'Achievement not found.'}), 404
    else:
        return jsonify({'message': 'Achievement deleted successfully!'}), 200
    
    
def delete_achievement_by_name():
    try:
        data = request.json
        name = data.get("name")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    if not name:
        return jsonify({'message': NO_INPUT_400}), 400

    filter_criteria = (Achievement.name == name)
    deleted_count = Achievement.delete_achievements_by_filter(filter_criteria)
    # Return result
    if deleted_count == 0:
        return jsonify({'message': 'Achievement not found.'}), 404
    else:
        return jsonify({'message': 'Achievement deleted successfully!'}), 200


def get_achievement_by_id():
    try:
        data = request.json
        id = data.get("id")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    if not id:
        return jsonify({'message': NO_INPUT_400}), 400

    filter_criteria = (Achievement.id == id)
    achievement = Achievement.find_one_achievement_by_filter(filter_criteria)
    if not achievement:
        return jsonify({'message': 'Achievement not found.'}), 404
    else:
        achievement_dict = {
            'id': achievement.id,
            'name': achievement.name,
            'content': achievement.content,
            'criteria': achievement.criteria
        }
        return jsonify(achievement_dict), 200   
    
    
def get_achievement_by_name():
    try:
        data = request.json
        name = data.get("name")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    if not name:
        return jsonify({'message': NO_INPUT_400}), 400

    filter_criteria = (Achievement.name == name)
    achievement = Achievement.find_one_achievement_by_filter(filter_criteria)
    if not achievement:
        return jsonify({'message': 'Achievement not found.'}), 404
    else:
        achievement_dict = {
            'id': achievement.id,
            'name': achievement.name,
            'content': achievement.content,
            'criteria': achievement.criteria
        }
        return jsonify(achievement_dict), 200
    
def get_all_achievements():
    filter_criteria = True
    achievements = Achievement.find_all_achievements_by_filter(filter_criteria)
    achievements_list = []
    for achievement in achievements:
        achievement_dict = {
            'id': achievement.id,
            'name': achievement.name,
            'content': achievement.content,
            'criteria': achievement.criteria
        }
        achievements_list.append(achievement_dict)
    return jsonify(achievements_list), 200

