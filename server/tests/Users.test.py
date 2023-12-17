import unittest
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

import os, sys
current_directory = os.path.dirname(os.path.abspath(__file__))
server_directory = os.path.abspath(os.path.join(current_directory, '..'))
sys.path.append(server_directory)
from database.db import db
from models.Users.model import *

class TestUserFunctions(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        # Create a session for testing
        cls.Session = db['Session']
        cls.Base = db['Base']
        cls.engine = db['engine']
        cls.Session.configure(bind=cls.engine)
        cls.session = cls.Session()
        cls.Base.metadata.create_all(cls.engine)

    @classmethod
    def tearDownClass(cls):
        # Clean up after testing
        cls.session.rollback()
        cls.session.close()
        cls.Base.metadata.drop_all(cls.engine)

    

    def test_create_new_user(self):
        # Test the create_new_user function
        initial_user_count = self.session.query(User).count()

        # Call the create_new_user function
        create_new_user('TestUser', 'password', UserRole.LEANER, 'test@example.com')

        # Check if the user count increased after creating a new user
        new_user_count = self.session.query(User).count()
        self.assertEqual(new_user_count, initial_user_count + 1)

if __name__ == '__main__':
    unittest.main()
