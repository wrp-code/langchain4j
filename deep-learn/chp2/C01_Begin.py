import random
import torch
#
def synthetic_data(w, b, num_examples):
    """生成y = X * w + b + 噪声"""
    # 正太分布（平均值，方差）
    X = torch.normal(0, 1, (num_examples, len(w)))
    y = torch.matmul(X, w) + b
    y += torch.normal(0, 0.01, y.shape)
    # y.reshape((-1, 1)) 列向量形式
    return X, y.reshape((-1, 1))

true_w = torch.tensor([2, -3.4])
true_b = 4.2
features, labels = synthetic_data(true_w, true_b, 1000)
print('features: ' , features[0])
print('label: ' , labels[0])

# 批量数据发生器
def data_iter(batch_size, features, labels):
    num_examples = len(features)
    indices = list(range(num_examples))
    random.shuffle(indices)
    for i in range(0, num_examples, batch_size):
        batch_indices = indices[i:min(i+batch_size, num_examples)]
        yield features[batch_indices], labels[batch_indices]

# 批量大小
batch_size = 10

# 定义初始化参数模型
w = torch.normal(0, 0.01, size=(2,1), requires_grad=True)
b = torch.zeros(1, requires_grad=True)

# 定义模型
def linreg(X, w, b):
    """线性回归模型"""
    return torch.matmul(X, w) + b

# 定义损失函数
def squared_loss(y_hat, y):
    """均方损失"""
    return (y_hat - y.reshape(y_hat.shape))**2 / 2

# 定义优化算法
def sgd(params, lr, batch_size):
    """小批量随机梯度下降"""
    with torch.no_grad():
        for param in params:
            param -= lr * param.grad / batch_size
            param.grad.zero_()

# 学习率
lr = 0.03
num_epochs = 3
net = linreg
loss = squared_loss

# 扫一遍数据
for epoch in range(num_epochs):
    # 批量获取数据
    for X, y in data_iter(batch_size, features, labels):
        l = loss(net(X, w, b), y)
        #
        l.sum().backward()
        sgd([w, b], lr, batch_size)

    with torch.no_grad():
        train_l = loss(net(features, w, b), labels)
        print(f'epoch {epoch + 1}, loss {float(train_l.mean()):f}')

print(f'w的估计误差：{true_w - w.reshape(true_w.shape)}')
print(f'b的估计误差：{true_b - b}')
