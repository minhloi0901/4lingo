from flask import jsonify, Response, request
from models.Questions_model import Question
import json


def add_new_question():
    try:
        data = request.json
        score = data.get('score')
        content = data.get('content')
        answer = data.get('answer')
        explanation = data.get('explanation')
        choice = data.get('choice')
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
        
    new_question = Question.create_new_question(score, content, answer, explanation, choice)
    return jsonify({'message': 'New question created successfully!'}), 201

def delete_question_by_id():
    try:
        data = request.json
        question_id = data.get('question_id')
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    filter_criteria = Question.id == question_id
    deleted_count = Question.delete_questions_by_filter(filter_criteria)

    if deleted_count == 0:
        return jsonify({'message': 'Question not found or you do not have permission to delete it.'}), 404
    else:
        return jsonify({'message': 'Question deleted successfully!'}), 200

def update_question_by_id():
    try:
        data = request.json
        question_id = data.get('question_id')
        update_data = data.get('update_data')
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    filter_criteria = Question.id == question_id
    Question.update_question_by_filter(filter_criteria, update_data)
    return jsonify({'message': 'Question updated successfully!'}), 200

def find_one_question_by_id():
    try:
        data = request.json
        question_id = data.get('question_id')
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
    
    filter_criteria = Question.id == question_id
    question = Question.find_one_question_by_filter(filter_criteria)

    if question:
        question_data = {
            'id': question.id,
            'score': question.score,
            'content': question.content,
            'answer': question.answer,
            'explanation': question.explanation,
            'choice': question.choice 
        }
        response = Response(json.dumps(question_data, ensure_ascii=False), content_type="application/json; charset=utf-8")
        return response, 200
    else:
        return jsonify({'message': 'Question not found'}), 404

def find_all_questions_by_filter(criteria):
    try:
        data = request.json
        filter_criteria = data.get('criteria')
    except ValueError:
        return jsonify({'message': 'Invalid JSON data in the request body'}), 400
        
    questions = Question.find_all_questions_by_filter(filter_criteria)
    question_list = []

    for question in questions:
        question_data = {
            'id': question.id,
            'score': question.score,
            'content': question.content,
            'answer': question.answer,
            'explanation': question.explanation,
            'choice': question.choice  
        }
        question_list.append(question_data)

    response = Response(json.dumps(question_list, ensure_ascii=False), content_type="application/json; charset=utf-8")
    return response, 200