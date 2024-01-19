from enum import Enum
from operator import and_
from sqlalchemy import Integer, Column, String
from sqlalchemy import Enum as SQLAlchemyEnum

from database.db import db

Session = db['Session']
session = Session()
Base = db['Base']

class Achievement(Base):
    __tablename__ = 'achievement'
    achievement_id = Column(String(32), primary_key=True)
    achievement_name = Column(String(100), nullable=False, unique=True)
    criteria = Column(String(200))
    
    # Class method to create a new achievement
    @classmethod
    def create_new_achievement(cls, achievement_id, achievement_name, criteria=None):
        new_achievement = cls(
            achievement_id=achievement_id,
            achievement_name=achievement_name,
            criteria=criteria
        )
        session.add(new_achievement)
        session.commit()
        print("New achievement created.")
        return new_achievement
    
    # Class method to delete achievements by filter 
    @classmethod
    def delete_achievements_by_filter(cls, filter_criteria):
        deleted_count = session.query(cls).filter(filter_criteria).delete()
        session.commit()
        if deleted_count == 0:
            print("No achievement deleted.")
        elif deleted_count == 1:
            print("1 achievement deleted.")
        else:
            print(f"{deleted_count} achievements deleted.")

    # Class method to find achievements by filter
    @classmethod
    def find_one_achievement_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).first()
    
    # Class method to find all achievements by filter
    @classmethod
    def find_all_achievements_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).all()
    
    # Class method to update achievements by filter
    @classmethod
    def update_achievement_by_filter(cls, filter_criteria, update_data):
        updated_count = session.query(cls).filter(filter_criteria).update(update_data)
        session.commit()
        if updated_count == 0:
            print("No achievement updated.")
        elif updated_count == 1:
            print("1 achievement updated.")
        else:
            print(f"{updated_count} achievements updated.")
        
