from sqlalchemy import Column, Integer, String, TIMESTAMP, ForeignKey, and_, func
from sqlalchemy.orm import relationship
from database.db import db
from datetime import datetime

Session = db['Session']
session = Session()
Base = db['Base']

class Lesson(Base):
    __tablename__ = 'lesson'
    id = Column(Integer, primary_key=True)
    lesson_name = Column(String(128), nullable=False, unique=True)
    author = Column(Integer, ForeignKey('user.id'), nullable=False)
    time_created = Column(TIMESTAMP, nullable=False, default=func.now())
    lesson_type = Column(String(1024), nullable=False)
    score = Column(Integer, default=0)
    popularity_score = Column(Integer, default=0)
    lesson_level = Column(Integer)
    number_of_questions = Column(Integer, default=0)

    # Define the relationship
    author_link = relationship('User', back_populates='lesson_author')
    questions = relationship('Question', back_populates='lesson')

    # Class method to create a new lesson
    @classmethod
    def create_new_lesson(cls, lesson_name, author, lesson_type, lesson_level):
        new_lesson = cls (
            lesson_name=lesson_name,
            author=author,
            lesson_type=lesson_type,
            lesson_level=lesson_level
        )

        session.add(new_lesson)
        session.commit()
        print("New lesson created.")
        return new_lesson
    
    # Class method to delete lessons by filter
    @classmethod
    def delete_lessons_by_filter(cls, filter_criteria):
        deleted_count = session.query(cls).filter(filter_criteria).delete()
        session.commit()
        if deleted_count == 0:
            print("No lesson deleted.")
        elif deleted_count == 1:
            print("1 lesson deleted.")
        else:
            print(f"{deleted_count} lessons deleted.")
    
    # Class method to find lessons by filter
    @classmethod
    def find_one_lesson_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).first()
    
    # Class method to find all lessons by filter
    @classmethod
    def find_all_lessons_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).all()
    
    # Class method to update lessons by filter
    @classmethod
    def update_lessons_by_filter(cls, filter_criteria, update_data):
        updated_count = session.query(cls).filter(filter_criteria).update(update_data)
        session.commit()
        if updated_count == 0:
            print("No lesson updated.")
        elif updated_count == 1:
            print("1 lesson updated.")
        else:
            print(f"{updated_count} lessons updated.")