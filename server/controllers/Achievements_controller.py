# Users.controller.py
from flask import jsonify

from errors.Errors import ALREADY_EXIST, NO_INPUT_400, INVALID_INPUT_422
from models.Achievements_model import Achievement
from database.db import db
from middlewares.Auth_validator import validate_password, validate_username, validate_email, validate_phone_number
from middlewares.Auth_middleware import hash_password

Session = db['Session']
session = Session()
Base = db['Base']

salt_rounds = 8

def create_new_achievement(achievement_id, achievement_name, criteria=None):
    # Check if achievement_id and achievement_name are provided
    if not achievement_id or not achievement_name:
        return NO_INPUT_400
    
    # Check for duplicate achievement_id
    existing_achievement = session.query(Achievement).filter(Achievement.achievement_id == achievement_id).first()
    if existing_achievement:
        return ALREADY_EXIST
    
    # Create a new achievement if all checks pass
    new_achievement = Achievement.create_new_achievement(achievement_id, achievement_name, criteria)
    return jsonify({'message': 'New achievement created.'})
    
    
def get_achievement_by_id(achievement_id):
    filter_criteria = Achievement.achievement_id == achievement_id
    achievement = Achievement.find_one_achievement_by_filter(filter_criteria)
    if not achievement:
        return jsonify({'message': 'Achievement does not exist.'})
    return jsonify(achievement.serialize())


def get_achievement_by_name(achievement_name):
    filter_criteria = Achievement.achievement_name == achievement_name
    achievement = Achievement.find_one_achievement_by_filter(filter_criteria)
    if not achievement:
        return jsonify({'message': 'Achievement does not exist.'})
    return jsonify(achievement.serialize())


def update_achievement_by_id(achievement_id, update_data):
    filter_criteria = Achievement.achievement_id == achievement_id
    updated_count = Achievement.update_achievement_by_filter(filter_criteria, update_data)
    if not updated_count:
        return jsonify({'message': 'Achievement does not exist.'})
    return jsonify({'message': f'Updated {updated_count} achievements.'})


def delete_achievement_by_id(achievement_id):
    filter_criteria = Achievement.achievement_id == achievement_id
    deleted_count = Achievement.delete_achievements_by_filter(filter_criteria)
    if not deleted_count:
        return jsonify({'message': 'Achievement does not exist.'})
    return jsonify({'message': 'Achievement deleted successfully.'})


def get_all_achievements():
    achievements = Achievement.find_all_achievements_by_filter(True)
    return jsonify(achievements.serialize())

