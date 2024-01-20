from flask import jsonify

from ..errors.Errors import ALREADY_EXIST, NO_INPUT_400, DOES_NOT_EXIST
from ..models.Communities_model import Community
from ..models.Users_model import User
from ..database.db import db


Session = db['Session']
session = Session()
Base = db['Base']


def create_new_post(cls, community_id, author, content):
    # Check if community_id and author are provided
    if not community_id or not author:
        return NO_INPUT_400
    
    # Check if community exists
    existing_community = session.query(Community).filter(Community.id == community_id).first()
    if not existing_community:
        return DOES_NOT_EXIST
    
    # Check if author exists
    existing_author = session.query(User).filter(User.id == author).first()
    if not existing_author:
        return DOES_NOT_EXIST
    
    new_post = cls.create_post(community_id, author, content)
    return jsonify({'message': 'New post created.'})


def get_post_by_id(cls, community_id, id):
    filter_criteria = cls.id == id and cls.community_id == community_id
    post = cls.find_one_post_by_filter(filter_criteria)
    if not post:
        return jsonify({'message': 'Post does not exist.'})
    return jsonify(post.serialize())


def update_post_by_id(cls, community_id, id, update_data):
    filter_criteria = cls.id == id and  cls.community_id == community_id
    updated_count = cls.update_post(filter_criteria, update_data)
    if not updated_count:
        return jsonify({'message': 'Post does not exist.'})
    return jsonify({'message': f'Updated {updated_count} posts.'})


def delete_post_by_id(cls, community_id, id):
    filter_criteria = cls.id == id and cls.community_id == community_id
    deleted_count = cls.delete_posts_by_filter(filter_criteria)
    if not deleted_count:
        return jsonify({'message': 'Post does not exist.'})
    return jsonify({'message': 'Post deleted successfully.'})


def get_all_posts(cls):
    posts = cls.find_all_posts_by_filter(True)
    return jsonify(posts.serialize())


def get_all_posts_by_community_id(cls, community_id):
    filter_criteria = cls.community_id == community_id
    posts = cls.find_all_posts_by_filter(filter_criteria)
    return jsonify(posts.serialize())



