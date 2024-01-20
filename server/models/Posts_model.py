from sqlalchemy import Integer, Column, String, DateTime, ForeignKey, TIMESTAMP, func, PrimaryKeyConstraint, ForeignKeyConstraint
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base

import os, sys
from database.db import db
from sqlalchemy.ext.declarative import declarative_base

from Communities_model import Community
from Users_model import User

Session = db['Session']
session = Session()
Base = db['Base']


class Post(Base):
#     CREATE TABLE IF NOT EXISTS post (
# 	community_id INT NOT NULL, 
# 	id INT NOT NULL,
# 	author INT NOT NULL,
# 	content VARCHAR(1024) NOT NULL,
# 	date_posted TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
# 	number_of_likes INT DEFAULT 0,
# 	number_of_dislikes INT DEFAULT 0,
# 	number_of_views INT DEFAULT 0,
#     PRIMARY KEY (community_id, id),
# 	FOREIGN KEY (community_id) REFERENCES community(id) ON DELETE CASCADE ON UPDATE CASCADE,
# 	FOREIGN KEY (author) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE
# );
	__tablename__ = 'post'
	community_id = Column(Integer, ForeignKey('community.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False, primary_key=True)
	id = Column(Integer, primary_key=True)
	author = Column(Integer, ForeignKey('user.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False)
	content = Column(String(1024), nullable=False)
	date_posted = Column(TIMESTAMP, nullable=False, server_default=func.now())
	number_of_likes = Column(Integer, default=0)
	number_of_dislikes = Column(Integer, default=0)
	number_of_views = Column(Integer, default=0)
 
	
	@classmethod
	def create_post(cls, community_id, author, content):
		new_post = cls (
			community_id=community_id,
			id=id,
			author=author,
			content=content
		)
		session.add(new_post)
		session.commit()
		print("New post created.")
		return new_post

	@classmethod
	def delete_posts_by_filter(cls, filter_criteria):
		deleted_count = session.query(cls).filter(filter_criteria).delete()
		session.commit()
		print(f"Deleted {deleted_count} posts.")
		return deleted_count

	@classmethod
	def find_one_post_by_filter(cls, filter_criteria):
		return session.query(cls).filter(filter_criteria).first()

	@classmethod
	def find_all_posts_by_filter(cls, filter_criteria):
		return session.query(cls).filter(filter_criteria).all()

	@classmethod
	def update_post(cls, filter_criteria, update_data):
		updated_count = session.query(cls).filter(filter_criteria).update(update_data)
		session.commit()
		print(f"Updated {updated_count} communities.")
		return updated_count




