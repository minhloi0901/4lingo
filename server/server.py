from flask import Flask
from routes.Users_route import user_router
from routes.Auth_route import auth_router
from routes.Dictionary_route import dict_router
from routes.Profile_route import profile_router
from routes.Questions_route import question_router
from routes.Vocabularies_route import vocabulary_router
from routes.Lessons_route import lessons_router
from routes.Achievements_route import achievement_router
from routes.Users_Achievements_route import user_achievement_router
from routes.Communities_route import community_router
from routes.Answers_route import answer_router

app = Flask(__name__)

app.register_blueprint(user_router, url_prefix='/users')
app.register_blueprint(auth_router, url_prefix='/auth')
app.register_blueprint(dict_router, url_prefix='/dictionary')
app.register_blueprint(profile_router, url_prefix='/profile')
app.register_blueprint(question_router, url_prefix='/questions')

app.register_blueprint(vocabulary_router, url_prefix='/vocabularies')
app.register_blueprint(lessons_router, url_prefix='/lessons')
app.register_blueprint(achievement_router, url_prefix='/achievements')
app.register_blueprint(user_achievement_router, url_prefix='/users_achievements')
app.register_blueprint(community_router, url_prefix='/communities')

@app.route('/')
def root():
    return 'this is 4lingo backend server'

if __name__ == '__main__':
    app.run(debug=True) 