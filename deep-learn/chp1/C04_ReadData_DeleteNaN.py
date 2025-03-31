import pandas as pd
import torch

data = pd.read_csv('../data/house_tiny.csv')
print(data)

# 处理缺失值 删除法
# 求所有列的缺失值数量
missing_counts = data.isnull().sum()
print('missing_counts : ', missing_counts)
# 求缺失值最多的列
max_missing_column = missing_counts.idxmax()
print('max_missing_column : ', max_missing_column)
# 删除缺失值最多的列
data = data.drop(columns=[max_missing_column])
print(data)


x = torch.tensor(data.to_numpy(dtype=float))
print(x)
