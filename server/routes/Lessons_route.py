from flask import Blueprint, request, jsonify    
from controllers import Lessons_controller

lessons_router = Blueprint('lessons', __name__)

@lessons_router.route("/")
def root():
    return Lessons_controller.find_all_lessons()

@lessons_router.route("/add", methods=["POST"])
def add_new_lesson():
    data = request.json
    lesson_name = data.get('lesson_name')
    author = data.get('author')
    lesson_type = data.get('lesson_type')
    lesson_level = data.get('lesson_level')
    return Lessons_controller.add_new_lesson(lesson_name, author, lesson_type, lesson_level)

@lessons_router.route("/delete", methods=["DELETE"])
def delete_lesson_by_id(lesson_id):
    data = request.json
    lesson_id = data.get('lesson_id')
    return Lessons_controller.delete_lesson_by_id(lesson_id)

@lessons_router.route("/find", methods=["GET"])
def find_lesson():
    data = request.json
    token = data.get('token')
    lesson_type = data.get('lesson_type')
    return Lessons_controller.find_lesson(token, lesson_type)

@lessons_router.route("/find_by_id", methods=["GET"])
def find_lesson_by_id():
    data = request.json
    lesson_id = data.get('lesson_id')
    return Lessons_controller.find_one_lesson_by_id(lesson_id)
