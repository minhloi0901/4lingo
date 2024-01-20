from sqlalchemy import Integer, Column, String, DateTime, ForeignKey, TIMESTAMP, func, PrimaryKeyConstraint, ForeignKeyConstraint
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base

import os, sys
current_directory = os.path.dirname(os.path.abspath(__file__))
server_directory = os.path.abspath(os.path.join(current_directory, '..'))
sys.path.append(server_directory)
from database.db import db
from sqlalchemy.ext.declarative import declarative_base

from models.Communities_model import Community
from models.Users_model import User
from models.Posts_model import Post


Session = db['Session']
session = Session()
Base = db['Base']

class Comment(Base):
#     CREATE TABLE IF NOT EXISTS comment (
# 	community_id INT NOT NULL,
# 	post_id INT NOT NULL,
# 	id INT NOT NULL,
# 	author INT NOT NULL,
# 	parent INT NOT NULL DEFAULT 0,
# 	time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
# 	content NVARCHAR(1024),
# 	likes INT DEFAULT 0,
# 	dislikes INT DEFAULT 0,
# 	PRIMARY KEY (community_id, post_id, id),
# 	FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE ON UPDATE CASCADE,
# 	FOREIGN KEY (author) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,
# 	FOREIGN KEY (parent) REFERENCES comment(id) ON DELETE CASCADE ON UPDATE CASCADE
# );
    __tablename__ = 'comment'
    
    community_id = Column(Integer, ForeignKey('community.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False, primary_key=True)
    post_id = Column(Integer, ForeignKey('post.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False, primary_key=True)
    id = Column(Integer, primary_key=True)
    author = Column(Integer, ForeignKey('user.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False)
    parent = Column(Integer, ForeignKey('comment.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False, default=0)
    time = Column(TIMESTAMP, nullable=False, server_default=func.now())
    content = Column(String(1024))
    likes = Column(Integer, default=0)
    
    
    
    
    @classmethod
    def create_comment(cls, community_id, post_id, author, parent, content):
        new_comment = cls (
            community_id=community_id,
            post_id=post_id,
            author=author,
            parent=parent,
            content=content
        )
        
        session.add(new_comment)
        session.commit()
        print("New comment created.")
        return new_comment
    
    @classmethod
    def delete_comments_by_filter(cls, filter_criteria):
        deleted_count = session.query(cls).filter(filter_criteria).delete()
        session.commit()
        return deleted_count
    
    
    @classmethod
    def find_one_comment_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).first()
    
    @classmethod
    def find_all_comments_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).all()
    
    @classmethod
    def update_comment(cls, filter_criteria, update_data):
        updated_count = session.query(cls).filter(filter_criteria).update(update_data)
        session.commit()
        return updated_count
    
    
    
    