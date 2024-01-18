import re
from models.Users_model import User

def validate_password(password):
    # Check password length
    if not (8 <= len(password) <= 32):
        print(len(password))
        return False, 'Password must be between 8 and 32 characters long.'
    
    # Check password contains at least one lowercase, uppercase, number, and special character
    if not (re.search(r'[a-z]', password) and 
        re.search(r'[A-Z]', password) and 
        re.search(r'\d', password) and 
        re.search(r'\W', password)):
        return False, 'Password must contain at least one lowercase, uppercase, number, and special character.'
    
    # Check space password
    if ' ' in password or '\t' in password or '\n' in password:
        return False, 'Password must not contain any whitespace characters.'
    
    return True, 'Password is valid.'

def validate_username(username):
    # Check username is not empty
    if not username:
        return False, 'Username are required.'
    # Check for existing username
    filter_by_username = (User.username == username)
    existing_user = User.find_one_user_by_filter(filter_by_username)
    if existing_user:
        return False, 'Username already exists.'
    # Check username length
    if len(username) < 4:
        return False, 'Username must be at least 4 characters long.'
    
    # Check space username
    if ' ' in username or '\t' in username or '\n' in username:
        return False, 'Username must not contain any whitespace characters.'
    
    return True, 'Username is valid.'
    
def validate_email(email):
    # Check email is not empty
    if not email:
        return False, 'Email are required.'
     # Check for valid email format (add more detailed validation if needed)
    if '@' not in email or '.' not in email:
        return False, 'Invalid email format.'
        # Check for existing email
    filter_by_email = (User.email == email)
    existing_email_user = User.find_one_user_by_filter(filter_by_email)
    if existing_email_user:
        return False, 'Email already exists.'
    
    return True, 'Email is valid.'

def validate_phone_number(phone_number):
    # Check if phone number is provided and not empty
    if phone_number and phone_number.strip() != '':
        filter_by_phone_number = User.phone_number == phone_number
        existing_phone_user = User.find_one_user_by_filter(filter_by_phone_number)
        if existing_phone_user:
            return False, 'Phone number already exists.'
    
    # Check for existing phone number
    filter_by_phone_number = (User.phone_number == phone_number)
    existing_phone_user = User.find_one_user_by_filter(filter_by_phone_number)
    if existing_phone_user:
        return False, 'Phone number already exists.'
    
    return True, 'Phone number is valid.'