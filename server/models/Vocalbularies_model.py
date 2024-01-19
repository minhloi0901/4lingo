from enum import Enum
from sqlalchemy import Integer, Column, String, BLOB
from sqlalchemy import Enum as SQLAlchemyEnum
from database.db import db

Session = db['Session']
session = Session()
Base = db['Base']

class Vocabulary(Base):
    __tablename__ = 'vocabulary'
    id = Column(Integer, primary_key=True)
    user_id = Column(Integer, nullable=False)
    text = Column(String(128), nullable=False)
    meaning = Column(String(1024), nullable=False)
    pronun_es_us = Column(BLOB)
    pronun_en_uk = Column(BLOB)
    
    # Class method to create a new vocabulary entry
    @classmethod
    def create_new_vocabulary(cls, user_id, text, meaning, pronun_es_us=None, pronun_en_uk=None):
        new_vocabulary = cls (
            user_id=user_id,
            text=text,
            meaning=meaning,
            pronun_es_us=pronun_es_us,
            pronun_en_uk=pronun_en_uk
        )

        session.add(new_vocabulary)
        session.commit()
        print("New vocabulary entry created.")
        return new_vocabulary
    
    # Class method to delete vocabulary entries by filter 
    @classmethod
    def delete_vocabulary_entries_by_filter(cls, filter_criteria):
        deleted_count = session.query(cls).filter(filter_criteria).delete()
        session.commit()
        if deleted_count == 0:
            print("No vocabulary entries deleted.")
        elif deleted_count == 1:
            print("1 vocabulary entry deleted.")
        else:
            print(f"{deleted_count} vocabulary entries deleted.")

    # Class method to find a vocabulary entry by filter
    @classmethod
    def find_one_vocabulary_entry_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).first()
    
    # Class method to find all vocabulary entries by filter
    @classmethod
    def find_all_vocabulary_entries_by_filter(cls, filter_criteria):
        return session.query(cls).filter(filter_criteria).all()
    
    # Class method to update vocabulary entries by filter
    @classmethod
    def update_vocabulary_entry_by_filter(cls, filter_criteria, update_data):
        updated_count = session.query(cls).filter(filter_criteria).update(update_data)
        session.commit()
        if updated_count == 0:
            print("No vocabulary entries updated.")
        elif updated_count == 1:
            print("1 vocabulary entry updated.")
        else:
            print(f"{updated_count} vocabulary entries updated.")
