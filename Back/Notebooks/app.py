from flask import Flask, request

app = Flask(__name__)

from detect import *

@app.route('/')
def index():
  return "Index of the test" 
  
@app.route('/get_id/<tesi>/<diff>')
def get_id(tesi, diff):
  return f"Hello {tesi} {diff}" #ID here

@app.route("/upload", methods=['POST'])
def upload_file():
    from werkzeug.datastructures import FileStorage
    FileStorage(request.stream).save(os.path.join(app.config['/Documents'], filename))
    return 'OK', 200


if __name__ == '__main__':
    app.debug = True
    app.run(host="0.0.0.0")
