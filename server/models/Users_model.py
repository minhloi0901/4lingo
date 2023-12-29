from enum import Enum
from operator import and_
from sqlalchemy import Integer, Column, String
from sqlalchemy import Enum as SQLAlchemyEnum

from database.db import db

Session = db['Session']
session = Session()
Base = db['Base']

class UserRole(Enum):
    LEANER = "LEANER"
    TEACHER = "TEACHER"
    ADMIN = "ADMIN"

class User(Base):
    __tablename__ = 'user'
    id = Column(Integer, primary_key=True)
    username = Column(String(128), nullable=False, unique=True)
    password = Column(String(128), nullable=False)
    score = Column(Integer, default=0)
    rating = Column(Integer, default=0)
    role = Column(SQLAlchemyEnum(UserRole), nullable=False)
    phone_number = Column(String(15))
    email = Column(String(255), nullable=False, unique=True)

    # Class method to create a new user
    @classmethod
    def create_new_user(cls, username, password, role, email, phone_number=None):
        role = UserRole(role)
        new_user = cls (
            username=username,
            password=password,
            role=role,
            email=email,
            phone_number=phone_number
        )

        session.add(new_user)
        session.commit()
        print("New user created.")
        return new_user
    
    # Class method to delete users by filter 
    @classmethod
    def delete_users_by_filter(cls, filter_criteria):
        deleted_count = session.query(cls).filter(filter_criteria).delete()
        session.commit()
        if deleted_count == 0:
            print("No user deleted.")
        elif deleted_count == 1:
            print("1 user deleted.")
        else:
            print(f"{deleted_count} users deleted.")

    # Class method to find users by filter
    @classmethod
    def find_one_user_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).first()
    
    # Class method to find all users by filter
    @classmethod
    def find_all_users_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).all()
    
    # Class method to update users by filter
    @classmethod
    def update_user_by_filter(cls, filter_criteria, update_data):
        updated_count = session.query(cls).filter(filter_criteria).update(update_data)
        session.commit()
        if updated_count == 0:
            print("No user updated.")
        elif updated_count == 1:
            print("1 user updated.")
        else:
            print(f"{updated_count} users updated.")
            
            