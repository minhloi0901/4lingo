from sqlalchemy import Integer, Column, String, DateTime, ForeignKey
from sqlalchemy.orm import relationship, Mapped, mapped_column
from sqlalchemy.ext.declarative import declarative_base

# import os, sys
# current_directory = os.path.dirname(os.path.abspath(__file__))
# server_directory = os.path.abspath(os.path.join(current_directory, '..'))
# sys.path.append(server_directory)

from database.db import db
from sqlalchemy.ext.declarative import declarative_base

from models.Users_model import User, UserRole

Session = db['Session']
session = Session()
Base = db['Base']



class Community(Base):
#     CREATE TABLE IF NOT EXISTS community (
# 	id INT AUTO_INCREMENT,
# 	name VARCHAR(128) NOT NULL UNIQUE,
# 	manager INT NOT NULL, 
# 	description NVARCHAR(128),
# 	number_of_users INT DEFAULT 0,
# 	date_create DATETIME,
# 	PRIMARY KEY (id),
# 	FOREIGN KEY (manager) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE
# );


    __tablename__ = 'community'
    id = Column(Integer, primary_key=True)
    name = Column(String(128), nullable=False, unique=True)
    manager = Column(Integer, 
                     ForeignKey('user.id', ondelete='CASCADE', onupdate='CASCADE'), 
                     nullable=False)
    description = Column(String(128))
    number_of_users = Column(Integer, default=0)
    date_create = Column(DateTime)
    

   
    
    @classmethod
    def create_new_community(cls, name, manager, description=None):
        new_community = cls (
            name=name,
            manager=manager,
            description=description
        )
        
        session.add(new_community)
        session.commit()
        print("New community created.")
        return new_community
    
    
    @classmethod
    def delete_communities_by_filter(cls, filter_criteria):
        deleted_count = session.query(cls).filter(filter_criteria).delete()
        session.commit()
        print(f"Deleted {deleted_count} communities.")
        return deleted_count
    
    
    @classmethod
    def find_one_community_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).first()
    
    
    @classmethod
    def find_all_communities_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).all()
    
    
    @classmethod 
    def update_community(cls, filter_criteria, update_data):
        updated_count = session.query(cls).filter(filter_criteria).update(update_data)
        session.commit()
        print(f"Updated {updated_count} communities.")
        return updated_count
    
