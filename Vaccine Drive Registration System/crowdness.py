import pandas as pd
from sklearn import linear_model
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
from sklearn.model_selection import KFold
from sklearn.model_selection import cross_val_score
import holidays
from datetime import datetime
import numpy as np

center_ids = [100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128]
awareness = [0.92, 0.21, 0.46, 0.49, 0.19, 0.22, 0.14, 0.49, 0.36, 0.32, 0.88, 0.66, 0.54, 0.89, 0.98, 0.14, 0.08, 0.13, 0.95, 0.35, 0.46, 0.06, 0.92, 0.43, 0.3, 0.07, 0.34, 0.57, 0.83]
def center_to_awareness(center):
    return awareness[center_ids.index(center)]

def validate_model(model,csv_file):
    df = pd.read_csv(csv_file)
    y_test = df['crowd']
    x = df.loc[:,['csa','cva','hol','awareness']]
    y_pred = model.predict(x)
    return mean_squared_error(y_test,y_pred)

def get_model(dataset):
    df = pd.read_csv(dataset,index_col=False)
    df.dropna(axis=0,inplace=True)
    x = df.loc[:,['csa','cva','hol','awareness']]
    y = df['crowd']
    model = linear_model.LinearRegression()
    model.fit(x,y)
    return model
#model = get_model('/content/drive/MyDrive/SPD Dataset/Crowdness.csv')

def is_holiday(date_str):
    date = datetime.strptime(date_str,'%Y/%m/%d')
    india_holidays = holidays.India(years=[2021,2022])
    if date in india_holidays or date.weekday() in [5,6]:
        return 1
    return 0

def predict_crowd(csv_file):
    df = pd.read_csv(csv_file,index_col=False)
    input_df = df.loc[:,['csa','cva']]
    awareness = list()
    holidays = list()
    for date in df['date']:
        holidays.append(is_holiday(date))
    for center_id in df['center_id']:
        awareness.append(center_to_awareness(center_id))
    input_df['hol'] = holidays
    input_df['awareness'] = awareness
    model = get_model('Crowdness.csv')
    crowd = list(model.predict(input_df))
    return crowd
    #print("Output written in {} successfully".format(csv_file))
    
    
    
crowd = predict_crowd('sample.csv')
for crd in crowd:
    print(crd,"\n")
#print(validate_model(get_model('Crowdness.csv'),'Crowdness.csv'))

