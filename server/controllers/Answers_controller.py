from errors.Errors import ALREADY_EXIST, NO_INPUT_400, INVALID_INPUT_422
from models.Answers_model import Answer
from models.Questions_model import Question
from models.Users_model import User
from database.db import db
from flask import jsonify, request
from database.db import db
from middlewares.Auth_middleware import get_id_from_token

Session = db['Session']
session = Session()
Base = db['Base']

salt_rounds = 8


def create_new_answer():
    try:
        data = request.json
        user_id = data.get("user_id")
        question_id = data.get("question_id")
        content = data.get("content")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400

    if not user_id or not content or not question_id or not content:
        return jsonify({'message': "At least one of the parameters was not provided"}), 400
    
    user_id = session.query(User).filter(User.id == user_id).first()
    question_id = session.query(Question).filter(Question.id == question_id).first()
    
    if not user_id:
        return jsonify({'message': "User not found"}), 404
    
    if not question_id:
        return jsonify({'message': "Question not found"}), 404
    
    if user_id and question_id:
        return jsonify({'message': "Duplicate data"}), 400
    
    new_answer = Answer.create_new_answer(user_id, question_id, content)
    if not new_answer:
        return jsonify({'message': "Error creating answer"}), 400

    return jsonify({'message': 'New answer created successfully!'}), 201
    

def delete_answer_by_ids():
    try:
        data = request.json
        user_id = data.get("user_id")
        question_id = data.get("question_id")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400

    user_id = session.query(User).filter(User.id == user_id).first()
    question_id = session.query(Question).filter(Question.id == question_id).first()
    
    if not user_id:
        return jsonify({'message': "User not found"}), 404
    
    if not question_id:
        return jsonify({'message': "Question not found"}), 404

    
    delete_count = Answer.delete_answer_by_ids(user_id, question_id)

    return jsonify({'message': 'Answer deleted successfully!'}), 200
    
    
def get_answer_by_ids():
    try:
        data = request.json
        user_id = data.get("user_id")
        question_id = data.get("question_id")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    user_id = session.query(User).filter(User.id == user_id).first()
    question_id = session.query(Question).filter(Question.id == question_id).first()
    
    if not user_id:
        return jsonify({'message': "User not found"}), 404
    
    if not question_id:
        return jsonify({'message': "Question not found"}), 404 
    
    answer = Answer.find_one_answer_by_ids(user_id, question_id)
    
    ans_dict = {
        'user_id': answer.user_id,
        'question_id': answer.question_id,
        'content': answer.content
    }
    return jsonify(ans_dict), 200
    

def get_all_answers_by_user_id():
    try:
        data = request.json
        user_token = data.get("user_id")
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    is_valid_token, error_message = get_id_from_token(user_token)
    
    if not is_valid_token:
        return jsonify({'message': error_message}), 401

    user_id = error_message
    filter_criteria = (Answer.user_id == user_id)
    answers = Answer.find_all_answers_by_filter(filter_criteria)
    
    result_list = []
    for entry in answers:
        new_dict = {
            "user_id": entry.user_id,
            "question_id": entry.question_id,
            "content": entry.content
        }
        result_list.append(new_dict)
    
    return jsonify(result_list), 200
    

