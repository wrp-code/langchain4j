import torch
x = torch.arange(4.0, requires_grad=True)
print(x)
print(x.grad)
y = 2 * torch.dot(x,x)
print(y)

print(y.backward())
print(x.grad)

print(x.grad == 4 * x)


print(x.grad.zero_())
y = x * x
print(y.sum().backward())
print(x.grad)