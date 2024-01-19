from enum import Enum
from operator import and_
from sqlalchemy import Integer, Column, String, ForeignKey
from sqlalchemy import Enum as SQLAlchemyEnum
from models.Achievements_model import Achievement
from models.Users_model import User

from database.db import db


Session = db['Session']
session = Session()
Base = db['Base']

class Users_Achievements(Base):
    # CREATE TABLE IF NOT EXISTS user_achievement (
    #     user_id INT NOT NULL,
    #     achievement_id INT NOT NULL,
    #     date_achieved TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    #     PRIMARY KEY (user_id, achievement_id),
    #     FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
    #     FOREIGN KEY (achievement_id) REFERENCES achievement(id) ON DELETE CASCADE ON UPDATE CASCADE 
    # );

    __tablename__ = 'user_achievement'
    user_id = Column(Integer, 
                     ForeignKey('user.id', ondelete='CASCADE', onupdate='CASCADE'), 
                     primary_key=True)
    achievement_id = Column(Integer, 
                            ForeignKey('achievement.id', ondelete='CASCADE', onupdate='CASCADE'), 
                            primary_key=True)
    date_achieved = Column(String(255), nullable=False)
    
    
    # Class method to create a new user_achievement
    @classmethod
    def create_new_user_achievement(cls, user_id, achievement_id, date_achieved):
        existing_user = session.query(User).filter(User.id == user_id).first()
        if not existing_user:
            print(f"User with id '{user_id}' does not exist.")
            return None, "User does not exist."

        existing_achievement = session.query(Achievement).filter(Achievement.id == achievement_id).first()
        if not existing_achievement:
            print(f"Achievement with id '{achievement_id}' does not exist.")
            return None, "Achievement does not exist."
        
        existing_user_achievement = session.query(cls).filter(and_(cls.user_id == user_id, cls.achievement_id == achievement_id)).first()
        if existing_user_achievement:
            print(f"User with id '{user_id}' already has achievement with id '{achievement_id}'.")
            return None, "User already has achievement."
        
        new_user_achievement = cls (
            user_id=user_id,
            achievement_id=achievement_id,
            date_achieved=date_achieved
        )

        session.add(new_user_achievement)
        session.commit()
        print("New user_achievement created successfully!")
        return new_user_achievement, "New user_achievement created successfully!"
    
    # Class method to delete users_achievements by filter
    @classmethod
    def delete_users_achievements_by_filter(cls, filter_criteria):
        deleted_count = session.query(cls).filter(filter_criteria).delete()
        session.commit()
        return deleted_count
    
    @classmethod
    def find_one_user_achievement_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).first()
    
    @classmethod
    def find_all_users_achievements_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).all()


    @classmethod
    def update_users_achievements_by_filter(cls, filter_criteria, update_data):
        updated_count = session.query(cls).filter(filter_criteria).update(update_data)
        session.commit()
        return updated_count
    


