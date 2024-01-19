from flask import Blueprint, jsonify, request
from controllers import Questions_controller
import json

question_router = Blueprint('questions', __name__)

@question_router.route("/")
def root():
    # return 'this is 4lingo backend server - question router'
    return Questions_controller.find_all_questions_by_filter(True)

@question_router.route("/add", methods=["POST"])
def add_new_question():
    # data = request.json
    data = json.loads(request.data.decode('utf-8', errors='ignore'))
    score = data.get('score')
    content = data.get('content')
    answer = data.get('answer')
    explanation = data.get('explanation')
    choice = data.get('choice')
    return Questions_controller.add_new_question(score, content, answer, explanation, choice)
   
@question_router.route("/delete", methods=["DELETE"])
def delete_question_by_id():
    data = request.json
    question_id = data.get('question_id')
    return Questions_controller.delete_question_by_id(question_id)

@question_router.route("/update", methods=["PUT"])
def update_question_by_id():
    data = request.json
    question_id = data.get('question_id')
    update_data = data.get('update_data')
    return Questions_controller.update_question_by_id(question_id, update_data)

@question_router.route("/find", methods=["GET"])
def find_all_questions_by_filter():
    data = request.json
    filter_criteria = data.get('filter_criteria')
    return Questions_controller.find_all_questions_by_filter(filter_criteria)

