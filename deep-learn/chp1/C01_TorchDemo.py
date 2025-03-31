import torch

x = torch.arange(12)

# tensor([ 0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11])
print(x)

# torch.Size([12]) 张量（沿每个轴的长度）的形状
print(x.shape)

# 12 所以它的shape与它的size相同
print(x.numel())


# 一个3行4列的矩阵
# 等价于 x.reshape(-1,4)或x.reshape(3,-1) ， 自动推算
X = x.reshape(3, 4)
#tensor([[ 0,  1,  2,  3],
#        [ 4,  5,  6,  7],
#        [ 8,  9, 10, 11]])
print(X)

# 初始值位0，三维张量
a = torch.zeros((2, 3, 4))
print(a)
# 初始值位1，三维张量
b = torch.ones((2, 3, 4))
print(b)

# 初始值都从均值为0、标准差为1的标准高斯分布（正态分布）中随机采样
c = torch.randn(3, 4)
print(c)

# 自定义
d = torch.tensor([[2, 1, 4, 3], [1, 2, 3, 4], [4, 3, 2, 1]])
print(d)

# 张量表算数运算
x = torch.tensor([1,2,4,8])
y = torch.tensor([2,2,2,2])
print(x + y)
print(x - y)
print(x * y)
print(x / y)
print(x ** y)
print(torch.exp(x))

x = torch.arange(12, dtype=torch.float32).reshape(3,4)
y = torch.tensor([[2, 1, 4, 3], [1, 2, 3, 4], [4, 3, 2, 1]])
# 1维拼接
print(torch.cat((x,y), dim=0))
# 2维拼接
print(torch.cat((x,y), dim=1))

# 两张张量表各位置元素是否相同
print(x == y)
print(x < y)
print(x > y)

print('========================')

# 单元素张量
print(x.sum())

## 广播机制
a = torch.arange(3).reshape(3,1)
b = torch.arange(2).reshape(1,2)
print(a)
print(b)
print(a + b)

# 索引和切片
print(x[-1])
print(x[1:3])

x[1,2] = 9
print(x)

# 第一行和第二行的全部元素设置为12
x[0:2, :] = 12
print(x)

# 节省内存
## id 求y变量的内存地址
before = id(y)
y = y + x
print(id(y) == before)

# 改进
z = torch.zeros_like(y)
print('id(z) : ', id(z))
z[:] = x + y
print('id(z) : ', id(z))
before = id(x)
# 等于 x[:] = x + y
x += y
print(id(x) == before)


## torch张量 转化为 numpy张量
a = x.numpy()
## numpy张量 转化为 torch张量
b = torch.tensor(a)
# <class 'numpy.ndarray'>
print(type(a))
# <class 'torch.Tensor'>
print(type(b))

## 将单个张量转为标量
a = torch.tensor([3.5])
print(a)
print(a.item())
print(float(a))
print(int(a))