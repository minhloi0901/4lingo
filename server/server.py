from flask import Flask
from routes.Users_route import user_router
from routes.Auth_route import auth_router
from routes.Dictionary_route import dict_router
from routes.Achievements_route import achievement_router

app = Flask(__name__)

app.register_blueprint(user_router, url_prefix='/users')
app.register_blueprint(auth_router, url_prefix='/auth')
app.register_blueprint(dict_router, url_prefix='/dictionary')
app.register_blueprint(achievement_router, url_prefix='/achievements')

@app.route('/')
def root():
    return 'this is 4lingo backend server'

if __name__ == '__main__':
    app.run(debug=True) 
