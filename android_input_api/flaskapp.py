from flask import Flask, jsonify, request
import numpy as np
import json
import requests
import pandas as pd
from sklearn.preprocessing import StandardScaler

app = Flask(__name__)

@app.route('/')
def root():
    return 'Nothing here'

@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.json.get('instances')
        data = np.array(data)
        data = np.reshape(data, (1,-1))

        datasetTotal = pd.read_csv('/home/glennyohanes17/flaskapp/csv/datasetTotal.csv')

        X = datasetTotal.to_numpy()
        sc = StandardScaler()
        X = sc.fit(X)
        formatted = sc.transform(data)
        input_data = {'instances': formatted.tolist()}
        url = "https://predict-feizm4s2ta-et.a.run.app/v1/models/model/versions/1:predict"
        x = requests.post(url, json=input_data)
        return x.text
    except Exception as e:
        return "Error: {}".format(e)

if __name__ == '__main__':
    app.run(debug=True)
