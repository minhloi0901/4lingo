import unittest
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from server.database.db import db
from server.models.Users.model import *
from server.controllers.Users.controller import *

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
