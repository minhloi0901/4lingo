from flask import Blueprint, request
from controllers import Answers_controller

answer_router = Blueprint('answers', __name__)

@answer_router.route('/', methods=['POST'])
def create_new_answer():
    return Answers_controller.create_new_answer()


@answer_router.route('/', methods=['GET'])
def get_all_answers():
    return Answers_controller.get_all_answers_by_user_id()


