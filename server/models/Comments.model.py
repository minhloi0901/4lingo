from sqlalchemy import Integer, Column, String, DateTime, ForeignKey, TIMESTAMP, func, PrimaryKeyConstraint, ForeignKeyConstraint
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base

import os, sys
current_directory = os.path.dirname(os.path.abspath(__file__))
server_directory = os.path.abspath(os.path.join(current_directory, '..'))
sys.path.append(server_directory)
from database.db import db
from sqlalchemy.ext.declarative import declarative_base

from server.models.Communities.model import Community
from server.models.Users.model import User
from server.models.Posts.model import Post


Session = db['Session']
session = Session()
Base = declarative_base()

def Comment(Base):
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
    __table_name__ = 'comment'
    
    community_id = Column(Integer, ForeignKey('community.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False, primary_key=True)
    post_id = Column(Integer, ForeignKey('post.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False, primary_key=True)
    id = Column(Integer, primary_key=True)
    author = Column(Integer, ForeignKey('user.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False)
    parent = Column(Integer, ForeignKey('comment.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False, default=0)
    time = Column(TIMESTAMP, nullable=False, server_default=func.now())
    content = Column(String(1024))
    likes = Column(Integer, default=0)
    
    # Primary key
    __table_args__ = (
        PrimaryKeyConstraint('community_id', 'post_id', 'id'),
    )
    
    # Relationships
    commented_in = relationship('Post', back_populates='comments')
    commented_by = relationship('User', back_populates='comments')
    replies = relationship('Comment', back_populates='parent_comment')
    
    
    @classmethod
    def create_comment(cls, community_id, post_id, author, parent, content):
        # Check if community exists
        existing_community = session.query(Community).filter(Community.id == community_id).first()
        if not existing_community:
            print(f"Community does not exist with id: {community_id}")
            return None
        
        # Check if post exists
        existing_post = session.query(Post).filter(Post.id == post_id).first()
        if not existing_post:
            print(f"Post does not exist with id: {post_id}")
            return None
        
        # Check if user exists
        existing_user = session.query(User).filter(User.id == author).first()
        if not existing_user:
            print(f"User does not exist with id: {author}")
            return None
        
        # Check if parent comment exists
        existing_parent_comment = session.query(cls).filter(cls.id == parent).first()
        if not existing_parent_comment:
            print(f"Parent comment does not exist with id: {parent}")
            return None
        
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
        if deleted_count == 0:
            print("No comment deleted.")
        elif deleted_count == 1:
            print("1 comment deleted.")
        else:
            print(f"{deleted_count} comments deleted.")
    
    
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
        if updated_count == 0:
            print("No comment updated.")
        elif updated_count == 1:
            print("1 comment updated.")
        else:
            print(f"{updated_count} comments updated.")
    
    