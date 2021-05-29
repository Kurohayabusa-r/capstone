import pandas as pd
from sklearn.preprocessing import StandardScaler

total = pd.read_csv('./csv/datasetTotal.csv')
X = total.to_numpy()
sc = StandardScaler()
X = sc.fit(X)