import unittest
from sqlalchemy import and_

from models.Users_model import User, UserRole
from database.db import session

class UsersTestCase(unittest.TestCase):

    def test_create_new_user(self):
        new_user = User.create_new_user(
            username='teacher_loi123',
            password='LOI123456',
            role="TEACHER",
            email='teacher_loi@gmail.com',
            phone_number='12345678910'
        )
        # Add assertions if needed

    def test_delete_users_by_filter(self):
        filter_criteria = User.username == 'teacher_loi'
        User.delete_users_by_filter(filter_criteria)
        # Add assertions if needed

    def test_find_one_user_by_filter(self):
        filter_criteria = and_(User.role == UserRole.LEARNER, User.phone_number == None)
        user = User.find_one_user_by_filter(filter_criteria)
        self.assertIsNotNone(user)
        # Add more assertions as needed

    def test_find_all_users_by_filter(self):
        filter_criteria = User.role == UserRole.LEARNER
        users = User.find_all_users_by_filter(filter_criteria)
        self.assertTrue(len(users) > 0)
        # Add more assertions as needed

    def test_update_user_by_filter(self):
        filter_criteria = and_(User.role == UserRole.LEARNER, User.phone_number == None)
        User.update_user_by_filter(filter_criteria, {User.phone_number: '01691234567'})
        # Add assertions if needed

    def test_get_all_users(self):
        users = session.query(User).all()
        self.assertTrue(len(users) > 0)
        # Add more assertions as needed

if __name__ == '__main__':
    unittest.main()
