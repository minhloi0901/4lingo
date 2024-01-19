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
    return Questions_controller.add_new_question()
   
   
@question_router.route("/delete", methods=["DELETE"])
def delete_question_by_id():
    return Questions_controller.delete_question_by_id()

@question_router.route("/update", methods=["PUT"])
def update_question_by_id():
    return Questions_controller.update_question_by_id()

@question_router.route("/find", methods=["GET"])
def find_all_questions_by_filter():
    return Questions_controller.find_all_questions_by_filter()

