import pandas as pd
import numpy as np
#import random
import matplotlib.pyplot as plt
#import os
#from matplotlib.pylab import rcParams
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
from tensorflow.keras.wrappers.scikit_learn import KerasRegressor
from sklearn.model_selection import cross_val_score, KFold, train_test_split
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import StandardScaler
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

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.01, random_state=0)

#reg = LinearRegression().fit(X, y)
sc = StandardScaler()
X_train = sc.fit_transform(X_train)
X_test = sc.fit_transform(X_test)

def model():
	# create model
	model = Sequential()
	model.add(Dense(32, input_dim=5, kernel_initializer='normal', activation='relu'))
	model.add(Dense(32, kernel_initializer='normal', activation='relu'))
	model.add(Dense(32, kernel_initializer='normal', activation='relu'))
	model.add(Dense(1, kernel_initializer='normal'))
	# Compile model
	model.compile(loss='mean_squared_error', optimizer='adam')
	return model


#arr = datasetTotal.to_numpy()

# print(X, y)

# estimators = []
# estimators.append(('standardize', StandardScaler()))
# estimators.append(('mlp', KerasRegressor(build_fn=model, epochs=50, batch_size=5, verbose=0)))
# pipeline = Pipeline(estimators)
# kfold = KFold(n_splits=10)
# results = cross_val_score(pipeline, X, y, cv=kfold)
# print("Baseline: %.2f (%.2f) MSE" % (results.mean(), results.std()))

model_real = model()
model_real.fit(X_train, y_train, batch_size=10, epochs=150)

y_pred = model_real.predict(X_test)

plt.plot(y_test, color = 'red', label = 'Real data')
plt.plot(y_pred, color = 'blue', label = 'Predicted data')
plt.title('Prediction')
plt.legend()
plt.show()