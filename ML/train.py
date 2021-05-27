import pandas as pd
import numpy as np
import math
#import random
import matplotlib.pyplot as plt
#import os
#from matplotlib.pylab import rcParams
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Dropout
from tensorflow.keras.models import model_from_json, model_from_config, save_model
from sklearn.metrics import mean_squared_error
from sklearn.model_selection import cross_val_score, KFold, train_test_split
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler, RobustScaler, MinMaxScaler

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
#datasetTotal = datasetTotal.sort_values(['YEAR', 'MONTH', 'DAY'])

X = datasetTotal.filter(regex='^(?!Mag).*$').to_numpy()
y = datasetTotal.filter(regex='Mag').to_numpy()

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.001, random_state=0)

#reg = LinearRegression().fit(X, y)
rc = RobustScaler()
sc = StandardScaler()
X_train = sc.fit_transform(X_train)
X_test = sc.fit_transform(X_test)

def model():
	model = Sequential()
	model.add(Dense(512, input_dim=5, kernel_initializer='normal', activation='relu'))
	model.add(Dropout(0.2))
	model.add(Dense(256, kernel_initializer='normal', activation='relu'))
	model.add(Dropout(0.2))
	model.add(Dense(128, kernel_initializer='normal', activation='relu'))
	model.add(Dropout(0.2))
	model.add(Dense(1, kernel_initializer='normal'))
	model.compile(loss='mean_squared_error', optimizer='adam')
	return model

print(X_train)

model_real = model()
model_real.fit(X_train, y_train, batch_size=10, epochs=50)

y_pred = model_real.predict(X_test)

# model_json = model_real.to_json()
# with open("model.json", "w") as json_file:
#     json_file.write(model_json)
# # serialize weights to HDF5
# model_real.save_weights("model.h5")
# print("Saved model to disk")

model_real.save('.', save_format="tf")
 

plt.plot(y_test, color = 'red', label = 'Real data')
plt.plot(y_pred, color = 'blue', label = 'Predicted data')
plt.title('Prediction')
plt.legend()
plt.show()