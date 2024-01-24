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
    id = Column(Integer, primary_key=True)
    name = Column(String(1024), nullable=False)
    content = Column(String(1024))
    criteria = Column(String(1024), nullable=False)
    
    # Class method to create a new achievement
    @classmethod
    def create_new_achievement(cls, name, content, criteria):
        new_achievement = cls (
            name=name,
            content=content,
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
        return deleted_count
            
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
        return updated_count
    
    