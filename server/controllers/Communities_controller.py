from flask import jsonify

from ..errors.Errors import ALREADY_EXIST, NO_INPUT_400, DOES_NOT_EXIST
from ..models.Communities_model import Community
from ..models.Users_model import User
from ..database.db import db

Session = db['Session']
session = Session()
Base = db['Base']


def create_new_community(cls, name, manager, description=None):
    # Check if name and manager are provided
    if not name or not manager:
        return NO_INPUT_400
    
    # Check if manager exists
    existing_manager = session.query(User).filter(User.id == manager).first()
    if not existing_manager:
        return DOES_NOT_EXIST
    
    # Check if community already exists
    existing_community = session.query(cls).filter(cls.name == name).first()
    if existing_community:
        return ALREADY_EXIST
    
    new_community = cls.create_new_community(name, manager, description)
    return jsonify({'message': 'New community created.'})


def get_community_by_id(cls, id):
    filter_criteria = cls.id == id
    community = cls.find_one_community_by_filter(filter_criteria)
    if not community:
        return jsonify({'message': 'Community does not exist.'})
    return jsonify(community.serialize())


def update_community_by_id(cls, id, update_data):
    filter_criteria = cls.id == id
    updated_count = cls.update_community(filter_criteria, update_data)
    if not updated_count:
        return jsonify({'message': 'Community does not exist.'})
    return jsonify({'message': f'Updated {updated_count} communities.'})


def delete_community_by_id(cls, id):
    filter_criteria = cls.id == id
    deleted_count = cls.delete_communities_by_filter(filter_criteria)
    if not deleted_count:
        return jsonify({'message': 'Community does not exist.'})
    return jsonify({'message': 'Community deleted successfully.'})


def get_all_communities(cls):
    communities = cls.find_all_communities_by_filter(True)
    return jsonify(communities.serialize())





    
    
    
    