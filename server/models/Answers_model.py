from enum import Enum
from operator import and_
from sqlalchemy import Integer, Column, String, ForeignKey, cascade
from sqlalchemy import Enum as SQLAlchemyEnum
from models.Users_model import User
from models.Questions_model import Question
from database.db import db
from middlewares.Auth_middleware import get_id_from_token

Session = db['Session']
session = Session()
Base = db['Base']

class Answer(Base):
    # CREATE TABLE IF NOT EXISTS answer (
    #     user_id INT NOT NULL,
    #     question_id INT NOT NULL,
    #     content NVARCHAR(1024),
    #     PRIMARY KEY (user_id, question_id),
    #     FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
    #     FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE ON UPDATE CASCADE
    # );
    __tablename__ = 'answer'
    user_id = Column(Integer, 
                     ForeignKey('user.id', ondelete='CASCADE', onupdate='CASCADE'),
                     primary_key=True)
    question_id = Column(Integer, 
                         ForeignKey('question.id', ondelete='CASCADE', onupdate='CASCADE')
                         primary_key=True)
    content = Column(String(1024))
    
    
    # Class method to create a new answer
    @classmethod
    def create_new_answer(cls, name, content, criteria):
        new_answer = cls(
            name=name,
            content=content,
            criteria=criteria
        )

        session.add(new_answer)
        session.commit()
        print("New answer created.")
        return new_answer
    
    
    # Class method to delete answers by filter
    @classmethod
    def delete_answers_by_filter(cls, filter_criteria):
        deleted_count = session.query(cls).filter(filter_criteria).delete()
        session.commit()
        return deleted_count
            
    # Class method to find answers by filter
    @classmethod
    def find_one_answer_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).first()
    
    # Class method to find all answers by filter
    @classmethod
    def find_all_answers_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).all()
    
    
    # Class method to update answers by filter
    @classmethod
    def update_answer_by_filter(cls, filter_criteria, update_data):
        updated_count = session.query(cls).filter(filter_criteria).update(update_data)
        session.commit()
        return updated_count
    
    