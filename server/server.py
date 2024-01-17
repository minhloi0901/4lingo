from flask import Flask
from routes.Users_route import user_router
from routes.Auth_route import auth_router

app = Flask(__name__)

app.register_blueprint(user_router, url_prefix='/users')
app.register_blueprint(auth_router, url_prefix='/auth')

@app.route('/')
def root():
    return 'this is 4lingo backend server'

if __name__ == '__main__':
    app.run(debug=True) 
