import pandas as pd
import numpy as np
#import random
#import matplotlib.pyplot as plt
#import os
#from matplotlib.pylab import rcParams
#from tensorflow.keras.models import Sequential
#from tensorflow.keras.layers import Dense, LSTM, Dropout
#import sklearn.preprocessing as skpre
from sklearn.linear_model import LinearRegression

print("Hello")

dataset2009 = pd.read_csv('./csv/2009.csv')
dataset2010 = pd.read_csv('./csv/2010.csv')
dataset2011 = pd.read_csv('./csv/2011.csv')
dataset2012 = pd.read_csv('./csv/2012.csv')
dataset2013 = pd.read_csv('./csv/2013.csv')
dataset2014 = pd.read_csv('./csv/2014.csv')
dataset2015 = pd.read_csv('./csv/2015.csv')
dataset2016 = pd.read_csv('./csv/2016.csv')
dataset2017 = pd.read_csv('./csv/2017.csv')
dataset2018 = pd.read_csv('./csv/2018.csv')

datasetTotal = pd.DataFrame({})
datasetTotal = datasetTotal.append(dataset2009.sample(1000))
datasetTotal = datasetTotal.append(dataset2010.sample(1000))
datasetTotal = datasetTotal.append(dataset2011.sample(1000))
datasetTotal = datasetTotal.append(dataset2012.sample(1000))
datasetTotal = datasetTotal.append(dataset2013.sample(1000))
datasetTotal = datasetTotal.append(dataset2014.sample(1000))
datasetTotal = datasetTotal.append(dataset2015.sample(1000))
datasetTotal = datasetTotal.append(dataset2016.sample(1000))
datasetTotal = datasetTotal.append(dataset2017.sample(1000))
datasetTotal = datasetTotal.append(dataset2018.sample(1000))

datasetTotal = datasetTotal.drop('KEDALAMAN', axis=1)
datasetTotal = datasetTotal.sort_values(['YEAR', 'MONTH', 'DAY'])

X = datasetTotal.filter(regex='^(?!Mag).*$').to_numpy()
y = datasetTotal.filter(regex='Mag').to_numpy()

reg = LinearRegression().fit(X, y)

#arr = datasetTotal.to_numpy()

print(reg.score(X, y))