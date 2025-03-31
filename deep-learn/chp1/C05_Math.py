import torch

a = torch.arange(20, dtype=torch.float32).reshape(4,5)
print(a)
print(a.T)

b = a.clone()
print(b)
print(a + b)
print(a * b)

print(a.sum())
# 等价于sum()
print(a.sum(axis=[0,1]))
# 按列求和
print(a.sum(axis=0))
# 按行求和
print(a.sum(axis=1))
print(a)


## 平均值
print(a.mean())
print(a.mean(axis=0))

## 非降维
sum_a = a.sum(axis=0, keepdims=True)
print(sum_a)
print(a)
print(a / sum_a)
# 按列累加
print(a.cumsum(axis=0))

x = torch.arange(4, dtype=torch.float32)
y = torch.ones(4, dtype=torch.float32)
print(torch.dot(x,y))
print(torch.sum(x * y))

x = torch.arange(24).reshape(2,3,4)
# 2
print(len(x))
print(x)
sum_x = a.sum(axis=1)
print(sum_x)
print(x / sum_x)
print(x.sum(axis=[0,1,2]))