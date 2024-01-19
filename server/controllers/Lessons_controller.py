from flask import jsonify, request
from models.Lessons_model import Lesson
from models.Users_model import User
from models.Questions_model import Question
from middlewares.Auth_middleware import get_id_from_token
from sqlalchemy import and_

import json

def add_new_lesson(lesson_name, author, lesson_type, lesson_level):
    # check if lesson_name already exists
    filter_criteria = Lesson.lesson_name == lesson_name
    lesson = Lesson.find_one_lesson_by_filter(filter_criteria)
    if lesson:
        return jsonify({'message': 'Lesson name already exists'})
    
    # check if lesson_type and lesson_level already exists
    filter_criteria = and_(Lesson.lesson_type == lesson_type, Lesson.lesson_level == lesson_level)
    lesson = Lesson.find_one_lesson_by_filter(filter_criteria)
    if lesson:
        return jsonify({'message': 'Lesson type and level already exists'})

    new_lesson = Lesson.create_new_lesson(lesson_name, author, lesson_type, lesson_level)
    return jsonify({'message': 'New lesson created successfully!'})

def delete_lesson_by_id(lesson_id):
    filter_criteria = Lesson.id == lesson_id
    Lesson.delete_lessons_by_filter(filter_criteria)

    return jsonify({'message': 'Lesson deleted successfully!'})

def update_lesson_by_id(lesson_id):
    filter_criteria = Lesson.id == lesson_id
    update_data = json.loads(request.data.decode('utf-8', errors='ignore'))
    Lesson.update_lessons_by_filter(filter_criteria, update_data)

    return jsonify({'message': 'Lesson updated successfully!'})

def find_one_lesson_by_id(lesson_id):
    filter_criteria = Lesson.id == lesson_id
    lesson = Lesson.find_one_lesson_by_filter(filter_criteria)
    if lesson:
        lesson_data = {
            'id': lesson.id,
            'lesson_name': lesson.lesson_name,
            'author': lesson.author,
            'time_created': lesson.time_created,
            'lesson_type': lesson.lesson_type,
            'lesson_level': lesson.lesson_level,
            'score': lesson.score,
            'popularity_score': lesson.popularity_score,
            'number_of_questions': lesson.number_of_questions
        }
        return jsonify(lesson_data)
    else:
        return jsonify({'message': 'Lesson not found'})

def find_all_lessons():
    filter_criteria = True
    lessons = Lesson.find_all_lessons_by_filter(filter_criteria)
    lesson_list = []

    for lesson in lessons:
        lesson_data = {
            'id': lesson.id,
            'lesson_name': lesson.lesson_name,
            'author': lesson.author,
            'time_created': lesson.time_created,
            'lesson_type': lesson.lesson_type,
            'lesson_level': lesson.lesson_level,
            'score': lesson.score,
            'popularity_score': lesson.popularity_score,
            'number_of_questions': lesson.number_of_questions
        }
        lesson_list.append(lesson_data)

    response = jsonify(lesson_list)
    response.headers['Content-Type'] = 'application/json; charset=utf-8'
    return response

def find_lesson(token, lesson_type):
    is_valid_token, error_message = get_id_from_token(token)
    if not is_valid_token:
        return jsonify({'message': 'Invalid token'}), 400

    user_id = error_message
    # get user by id
    filter_criteria = User.id == user_id
    user = User.find_one_user_by_filter(filter_criteria)
    if not user:
        return jsonify({'message': 'User not found'}), 401
    
    # get lesson_level by lesson_type
    lesson_property_name = 'lesson_type_' + str(lesson_type)
    lesson_level = user.__getattribute__(lesson_property_name)
    
    # get lesson by lesson_level
    filter_criteria = Lesson.lesson_level == lesson_level
    found_lesson = Lesson.find_one_lesson_by_filter(filter_criteria)
    if not found_lesson:
        return jsonify({'message': 'Lesson not found'}), 402
    
    lesson_id = found_lesson.id

    # get all questions by lesson_id
    filter_criteria = Lesson.id == lesson_id
    questions = Question.find_all_questions_by_filter(filter_criteria)
    question_list = []
    for question in questions:
        question_data = {
            'id': question.id,
            'lesson_id': question.lesson_id,
            'score': question.score,
            'content': question.content,
            'answer': question.answer,
            'explanation': question.explanation,
            'choice': question.choice
        }
        question_list.append(question_data)
    
    response = jsonify(question_list)
    response.headers['Content-Type'] = 'application/json; charset=utf-8'
    return response, 200
