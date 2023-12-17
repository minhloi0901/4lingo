from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base
from config.config import config 

USER = config['USER']
PASSWORD = config['PASSWORD']
HOST = config['HOST']
DB = config['DB']

DB_URL = f"mysql://{USER}:{PASSWORD}@{HOST}/{DB}"

engine = create_engine(DB_URL)
Session = sessionmaker(bind=engine)
Base = declarative_base()

# Create the 'db' variable for other files to import
db = {
    'engine': engine,
    'Session': Session,
    'Base': Base
}
