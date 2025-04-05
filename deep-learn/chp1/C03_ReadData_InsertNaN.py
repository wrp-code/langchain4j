import pandas as pd
import torch

data = pd.read_csv('../data/house_tiny.csv')
print(data)

# 处理缺失值 插值法
inputs, outputs = data.iloc[:, 0: 2], data.iloc[:, 2]
# 填充缺失值为均值
inputs = inputs.fillna(inputs.mean())
print(inputs)
## 由于“巷子类型”（“Alley”）列只接受两种类型的类别值“Pave”和“NaN”，
# pandas可以自动将此列转换为两列“Alley_Pave”和“Alley_nan”
inputs = pd.get_dummies(inputs, dummy_na=True)
print(inputs)

x = torch.tensor(inputs.to_numpy(dtype=float))
y = torch.tensor(outputs.to_numpy(dtype=float))
print(x)
print(y)



