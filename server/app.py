import subprocess
import os
from .database.db import db
from .models.Users_model import User
from .models.Communities_model import Community


if __name__ == "__main__":
    app = create_app()
    