from flask import jsonify, Response
from models.Questions_model import Question
import json

def add_new_question(score, content, answer, explanation, choice=None):
    new_question = Question.create_new_question(score, content, answer, explanation, choice)
    return jsonify({'message': 'New question created successfully!'})

def delete_question_by_id(question_id):
    filter_criteria = Question.id == question_id
    deleted_count = Question.delete_questions_by_filter(filter_criteria)

    if deleted_count == 0:
        return jsonify({'message': 'Question not found or you do not have permission to delete it.'})
    else:
        return jsonify({'message': 'Question deleted successfully!'})

def update_question_by_id(question_id, update_data):
    filter_criteria = Question.id == question_id
    Question.update_question_by_filter(filter_criteria, update_data)
    return jsonify({'message': 'Question updated successfully!'})

def find_one_question_by_id(question_id):
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
        return response
    else:
        return jsonify({'message': 'Question not found'})

def find_all_questions_by_filter(filter_criteria):
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
    return response