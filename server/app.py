from flask import Flask, render_template
from flask import request

app = Flask(__name__)

@app.route("/")
def hello_world():
    return "Hello World!"

@app.route("/post", methods=["POST"])
def hello_post():
    value=request.form['value']
    return value

@app.route("/user/<name>")
def user (name):
    return "<h1>Hello {}!!!</h1>".format(name)

if __name__ == "__main__":                              
    app.run(host='0.0.0.0', debug=True)
