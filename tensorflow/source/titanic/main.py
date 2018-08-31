import tensorflow as tf
import matplotlib.pyplot as plt
import pandas as pd


def read_data():
    train = pd.read_csv('data/train.csv')
    train['Sex'] = train['Sex'].apply(lambda sex: 0 if sex == 'male' else 1)
    train['Fare'] = train['Fare'] / train['Fare'].max()
    print(train[['Survived', 'Pclass', 'Sex', 'Age', 'SibSp', 'Parch', 'Ticket', 'Fare', 'Cabin', 'Embarked']])
    # print(train)


if __name__ == '__main__':
    read_data()
