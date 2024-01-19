from flask import Blueprint, request
from controllers import Vocabularies_controller

vocabulary_router = Blueprint('vocabularies', __name__)

@vocabulary_router.route("/add", methods=["POST"])
def add():
    return Vocabularies_controller.add_new_vocabulary()

@vocabulary_router.route("/delete", methods=["POST"])
def delete():
    return Vocabularies_controller.delete_vocabulary_by_text()

@vocabulary_router.route("/get_all_vocab", methods=["POST"])
def get_all_vocab():
    return Vocabularies_controller.get_all_vocabulary()