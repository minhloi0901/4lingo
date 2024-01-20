from flask import jsonify, request

from errors.Errors import ALREADY_EXIST, NO_INPUT_400, DOES_NOT_EXIST
from models.Communities_model import Community
from models.Posts_model import Post
from models.Users_model import User
from database.db import db


Session = db['Session']
session = Session()
Base = db['Base']


def create_post():
    try:
        data = request.json
        community_id = data.get('community_id')
        author = data.get('author')
        content = data.get('content')
        
        # Check if community_id and author are provided
        if not community_id or not author or not content:
            return jsonify({"message": "NO_INPUT_400"}), 400
        
        # Check if community exists
        existing_community = session.query(Community).filter(Community.id == community_id).first()
        if not existing_community:
            return jsonify({"message": "Community does not exist"}), 404
        
        # Check if author exists
        existing_author = session.query(User).filter(User.id == author).first()
        if not existing_author:
            return jsonify({"message": "Author does not exist"}), 404
        
        new_post = Post.create_post(community_id, author, content)
        return jsonify({"message": "New post created."}), 200
    
    except Exception as e:
        return jsonify({"message": str(e)}), 500


def get_post_by_id():
    try:
        data = request.json
        community_id = data.get('community_id')
        post_id = data.get('id')
        
        filter_criteria = Post.id == post_id and Post.community_id == community_id
        post = Post.find_one_post_by_filter(filter_criteria)
        
        if not post:
            return jsonify({"message": "Post does not exist."}), 404
        
        return jsonify(post.serialize()), 200
    
    except Exception as e:
        return jsonify({"message": str(e)}), 500


def update_post_by_id():
    try:
        data = request.json
        community_id = data.get('community_id')
        post_id = data.get('id')
        update_data = data.get('update_data')
        
        filter_criteria = Post.id == post_id and Post.community_id == community_id
        updated_count = Post.update_post(filter_criteria, update_data)
        
        if not updated_count:
            return jsonify({"message": "Post does not exist."}), 404
        
        return jsonify({"message": f"Updated {updated_count} posts."}), 200
    
    except Exception as e:
        return jsonify({"message": str(e)}), 500


def delete_post_by_id():
    try:
        data = request.json
        community_id = data.get('community_id')
        post_id = data.get('id')
        
        filter_criteria = Post.id == post_id and Post.community_id == community_id
        deleted_count = Post.delete_posts_by_filter(filter_criteria)
        
        if not deleted_count:
            return jsonify({"message": "Post does not exist."}), 404
        
        return jsonify({"message": "Post deleted successfully."}), 200
    
    except Exception as e:
        return jsonify({"message": str(e)}), 500


def get_all_posts():
    try:
        posts = Post.find_all_posts_by_filter(True)
        return jsonify(posts.serialize()), 200
    
    except Exception as e:
        return jsonify({"message": str(e)}), 500


def get_all_posts_by_community_id():
    try:
        data = request.json
        community_id = data.get('community_id')
        
        filter_criteria = Post.community_id == community_id
        posts = Post.find_all_posts_by_filter(filter_criteria)
        
        return jsonify(posts.serialize()), 200
    
    except Exception as e:
        return jsonify({"message": str(e)}), 500
