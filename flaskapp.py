from flask import Flask, jsonify, request
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

        dataset2009 = pd.read_csv('/home/glennyohanes17/flaskapp/csv/2009.csv')
        dataset2010 = pd.read_csv('/home/glennyohanes17/flaskapp/csv/2010.csv')
        dataset2011 = pd.read_csv('/home/glennyohanes17/flaskapp/csv/2011.csv')
        dataset2012 = pd.read_csv('/home/glennyohanes17/flaskapp/csv/2012.csv')
        dataset2013 = pd.read_csv('/home/glennyohanes17/flaskapp/csv/2013.csv')
        dataset2014 = pd.read_csv('/home/glennyohanes17/flaskapp/csv/2014.csv')
        dataset2015 = pd.read_csv('/home/glennyohanes17/flaskapp/csv/2015.csv')
        dataset2016 = pd.read_csv('/home/glennyohanes17/flaskapp/csv/2016.csv')
        dataset2017 = pd.read_csv('/home/glennyohanes17/flaskapp/csv/2017.csv')
        dataset2018 = pd.read_csv('/home/glennyohanes17/flaskapp/csv/2018.csv')

        datasetTotal = pd.DataFrame({})
        datasetTotal = datasetTotal.append(dataset2009)
        datasetTotal = datasetTotal.append(dataset2010)
        datasetTotal = datasetTotal.append(dataset2011)
        datasetTotal = datasetTotal.append(dataset2012)
        datasetTotal = datasetTotal.append(dataset2013)
        datasetTotal = datasetTotal.append(dataset2014)
        datasetTotal = datasetTotal.append(dataset2015)
        datasetTotal = datasetTotal.append(dataset2016)
        datasetTotal = datasetTotal.append(dataset2017)
        datasetTotal = datasetTotal.append(dataset2018)

        datasetTotal = datasetTotal.drop('KEDALAMAN', axis=1)

        X = datasetTotal.filter(regex='^(?!Mag).*$').to_numpy()
        sc = StandardScaler()
        X = sc.fit(X)
        formatted = sc.transform(data)
        url = "https://predict-feizm4s2ta-et.a.run.app/v1/models/model/versions/1:predict"
        input_data = {"instances": formatted.tolist()}
        x = requests.post(url, json=input_data)
        return x.text
    except Exception as e:
        return "Error: {}".format(e)

if __name__ == '__main__':
    app.run(debug=True)
