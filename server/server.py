from flask import Flask
from routes.Users_route import user_router

app = Flask(__name__)

# Register the Users routes from the Users_route.py file
app.register_blueprint(user_router, url_prefix='/users')

@app.route('/')
def root():
    return 'this is 4lingo backend server'

if __name__ == '__main__':
    app.run(debug=True) 
