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

Session = db['Session']
session = Session()
Base = declarative_base()


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
	__table_name__ = 'post'
	community_id = Column(Integer, ForeignKey('community.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False, primary_key=True)
	id = Column(Integer, primary_key=True)
	author = Column(Integer, ForeignKey('user.id', ondelete='CASCADE', onupdate='CASCADE'), nullable=False)
	content = Column(String(1024), nullable=False)
	date_posted = Column(TIMESTAMP, nullable=False, server_default=func.now())
	number_of_likes = Column(Integer, default=0)
	number_of_dislikes = Column(Integer, default=0)
	number_of_views = Column(Integer, default=0)
 
	# relationships
	posted_in = relationship("Community", back_populates="post")
	posted_by = relationship("User", back_populates="post")
 
	
	# Composite primary key 
	__table_args__ = (
		PrimaryKeyConstraint('community_id', 'id'),
	)
 
	
	@classmethod
	def create_post(cls, community_id, author, content):
		# Check if community exists
		existing_community = session.query(Community).filter(Community.id == community_id).first()
		if not existing_community:
			print(f"Community does not exist with id: {community_id}")
			return None

		# Check if author exists
		existing_author = session.query(User).filter(User.id == author).first()
		if not existing_author:
			print(f"Author does not exist with id: {author}")
			return None

		# Check if post already exists
		existing_post = session.query(cls).filter(cls.community_id == community_id, cls.id == id).first()
		if existing_post:
			print(f"Post already exists with id: {id}")
			return existing_post

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


