from flask import jsonify, request

from errors.Errors import ALREADY_EXIST, NO_INPUT_400, DOES_NOT_EXIST
from models.Communities_model import Community
from models.Comments_model import Comment
from models.Posts_model import Post
from models.Users_model import User
from database.db import db


Session = db['Session']
session = Session()
Base = db['Base']

def create_new_comment():
    try:
        data = request.json
        community_id = data.get("community_id")
        post_id = data.get("post_id")
        author = data.get("author")
        parent = data.get("parent")
        content = data.get("content")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    if not community_id or not post_id or not author or not content:
        return jsonify({'message': "At least one of the parameters was not provided"}), 400
    
    existing_community = session.query(Community).filter(Community.id == community_id).first()
    existing_post = session.query(Post).filter(Post.id == post_id).first()
    exiting_author = session.query(User).filter(User.id == author).first()
    
    if not existing_community:
        return jsonify({'message': "Community not found"}), 404
    
    if not existing_post:
        return jsonify({'message': "Post not found"}), 404
    
    if not exiting_author:
        return jsonify({'message': "Author not found"}), 404
    
    new_comment = Comment.create_comment(community_id, post_id, author, parent, content)
    if not new_comment:
        return jsonify({'message': "Error creating comment"}), 400
    
    return jsonify({'message': 'New comment created successfully!'}), 201


def get_all_comments_by_post():
    try:
        data = request.json
        post_id = data.get("post_id")
        community_id = data.get("community_id")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    if not post_id or not community_id:
        return jsonify({'message': "At least one of the parameters was not provided"}), 400
    
    existing_community = session.query(Community).filter(Community.id == community_id).first()
    existing_post = session.query(Post).filter(Post.id == post_id).first()
    
    if not existing_community:
        return jsonify({'message': "Community not found"}), 404
    
    if not existing_post:
        return jsonify({'message': "Post not found"}), 404
    
    filter_criteria = (Comment.post_id == post_id)
    comments = Comment.find_all_comments_by_filter(filter_criteria).all()
    
    result_dict = []
    for entry in comments:
        new_dict = {
            "community_id": entry.community_id,
            "post_id": entry.post_id,
            "id": entry.id,
            "author": entry.author,
            "parent": entry.parent,
            "time": entry.time,
            "content": entry.content,
            "likes": entry.likes
        }
        result_dict.append(new_dict)
    
    return jsonify(result_dict), 200
    
    
    

