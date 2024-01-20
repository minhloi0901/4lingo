from flask import jsonify, request

from errors.Errors import ALREADY_EXIST, NO_INPUT_400, DOES_NOT_EXIST
from models.Communities_model import Community
from models.Users_model import User
from database.db import db

Session = db['Session']
session = Session()
Base = db['Base']


def create_new_community():
    try:
        data = request.json
        name = data.get('name')
        manager = data.get('manager')
        description = data.get('description')
    except ValueError:
        return jsonify({'message': 'Invalid data format.'}), 400
    # Check if name and manager are provided
    if not name or not manager:
        return jsonify({'message': 'Please provide name and manager.'}), 400
    
    # Check if manager exists
    existing_manager = session.query(User).filter(User.id == manager).first()
    if not existing_manager:
        return jsonify({'message': 'Manager does not exist'}), 400
    
    # Check if community already exists
    existing_community = session.query(Community).filter(Community.name == name).first()
    if existing_community:
        return jsonify({'message': 'Community already exists'}), 400
    
    new_community = Community.create_new_community(name, manager, description)
    return jsonify({'message': 'New community created.'}), 201


def get_community_by_id():
    try:
        data = request.json
        id = data.get('id')
        
        filter_criteria = Community.id == id
        community = Community.find_one_community_by_filter(filter_criteria)
        if not community:
            return jsonify({'message': 'Community does not exist.'}), 404
        return jsonify(community.__id__)
    except ValueError:
        return jsonify({'message': 'Invalid data format.'}), 400


def update_community_by_id():
    try:
        data = request.json
        id = data.get('id')
        update_data = data.get('update_data')
        
        filter_criteria = Community.id == id
        updated_count = Community.update_community(filter_criteria, update_data)
        if not updated_count:
            return jsonify({'message': 'Community does not exist.'}), 404
        return jsonify({'message': f'Updated {updated_count} communities.'}), 200
    except ValueError:
        return jsonify({'message': 'Invalid data format.'}), 400


def delete_community_by_id():
    try:
        data = request.json
        id = data.get('id')
        
        filter_criteria = Community.id == id
        deleted_count = Community.delete_communities_by_filter(filter_criteria)
        if not deleted_count:
            return jsonify({'message': 'Community does not exist.'}), 404
        return jsonify({'message': 'Community deleted successfully.'}), 200
    except ValueError:
        return jsonify({'message': 'Invalid data format.'}), 400


def get_all_communities():
    try:
        communities = Community.find_all_communities_by_filter(True)
        
        result_list = []
        for entry in communities:
            new_dict = {
                "id": entry.id,
                "name": entry.name,
                "manager": entry.manager,
                "description": entry.description,
                "date_create": entry.date_create
            }
            result_list.append(new_dict)
        # Return result
        return jsonify(result_list), 200
    except ValueError:
        return jsonify({'message': 'Invalid data format.'}), 400

