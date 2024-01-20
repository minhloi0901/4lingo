from sqlalchemy import Column, Integer, String, NVARCHAR, TIMESTAMP, ForeignKey
from sqlalchemy.orm import relationship
from database.db import db

Session = db['Session']
session = Session()
Base = db['Base']

class Question(Base):
    __tablename__ = 'question'
    id = Column(Integer, primary_key=True)
    lesson_id = Column(Integer, ForeignKey('lesson.id'), nullable=False)
    score = Column(Integer)
    content = Column(NVARCHAR(1024))
    answer = Column(String(128))
    explanation = Column(NVARCHAR(1024))
    choice = Column(NVARCHAR(1024))

    # Define the relationship
    lesson = relationship('Lesson', back_populates='questions')

    # Class method to create a new question
    @classmethod 
    def create_new_question(cls, lesson_id, score, content, answer, explanation, choice=None):
        new_question = cls (
            lesson_id=lesson_id,
            score=score,
            content=content,
            answer=answer,
            explanation=explanation,
            choice=choice
        )

        session.add(new_question)
        session.commit()
        print("New question created.")
        return new_question
    
    # Class method to delete questions by filter
    @classmethod
    def delete_questions_by_filter(cls, filter_criteria):
        deleted_count = session.query(cls).filter(filter_criteria).delete()
        session.commit()
        return deleted_count

    # Class method to find questions by filter
    @classmethod
    def find_one_question_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).first()
    
    # Class method to find all questions by filter
    @classmethod
    def find_all_questions_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).all()
    
    # Class method to update questions by filter
    @classmethod
    def update_questions_by_filter(cls, filter_criteria, update_data):
        updated_count = session.query(cls).filter(filter_criteria).update(update_data)
        session.commit()
        if updated_count == 0:
            print("No question updated.")
        elif updated_count == 1:
            print("1 question updated.")
        else:
            print(f"{updated_count} questions updated.")