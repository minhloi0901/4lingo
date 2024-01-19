from flask import jsonify, request
from models.Vocalbularies_model import Vocabulary
from database.db import db
from middlewares.Auth_middleware import get_id_from_token

Session = db['Session']
session = Session()
Base = db['Base']

def add_new_vocabulary():
    try:
        data = request.json
        user_token = data.get("token")
        text = data.get("text")
        meaning = data.get("meaning")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400

    is_valid_token, error_message = get_id_from_token(user_token)
    if not is_valid_token:
        return jsonify({'message': error_message}), 401

    user_id = error_message
    new_vocabulary = Vocabulary.create_new_vocabulary(user_id, text, meaning)

    return jsonify({'message': 'New vocabulary entry created successfully!'}), 201

def delete_vocabulary_by_text():
    try:
        data = request.json
        user_token = data.get("token")
        text = data.get("text")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400

    is_valid_token, error_message = get_id_from_token(user_token)
    if not is_valid_token:
        return jsonify({'message': error_message}), 401

    user_id = error_message
    filter_criteria = (Vocabulary.text == text) & (Vocabulary.user_id == user_id)
    deleted_count = Vocabulary.delete_vocabulary_entries_by_filter(filter_criteria)

    if deleted_count == 0:
        return jsonify({'message': 'Vocabulary entry not found or you do not have permission to delete it.'}), 404
    else:
        return jsonify({'message': 'Vocabulary entry deleted successfully!'}), 200

def get_all_vocabulary():
    try:
        data = request.json
        user_token = data.get("token")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400

    is_valid_token, error_message = get_id_from_token(user_token)
    if not is_valid_token:
        return jsonify({'message': error_message}), 401

    user_id = error_message
    filter_criteria = Vocabulary.user_id == user_id
    vocab_entries = Vocabulary.find_all_vocabulary_entries_by_filter(filter_criteria)

    # Convert each entry to a dictionary for JSON response
    vocab_entries_list = []
    for entry in vocab_entries:
        entry_dict = {
            'id': entry.id,
            'text': entry.text,
            'meaning': entry.meaning
            # include other fields if necessary
        }
        vocab_entries_list.append(entry_dict)

    return jsonify(vocab_entries_list), 200
