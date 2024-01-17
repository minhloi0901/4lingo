from flask import request
from flask_validator import Validate

signup_scheme = {
    "username": {
        "type": "string",
        "required": True,
        "minlength": 4
    },
    "password": {
        "type": "string",
        "required": True,
        "minlength": 8,
        "regex": "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
        "messages": {
            "regex": "Password must be at least 8 characters, contains lowercase, uppercase, number, and special characters"
        }
    },
    "email": {
        "type": "string",
        "required": True,
        "email": True
    }
}

login_scheme = {
    "username": {
        "type": "string",
        "required": True
    },
    "password": {
        "type": "string",
        "required": True
    }
}