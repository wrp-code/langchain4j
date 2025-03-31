import numpy as np
from matplotlib_inline import backend_inline
from d2l import torch as d2l

def f(x):
    return 3 * x ** 2 - 4 * x

def numerical_lim(f, x, h):
    return (f(x + h) -f(x)) /h

h = 0.1
for i in range(10):
    print(f'h={h:.10f}, numerical limit={numerical_lim(f, 1, h):.10f}')
    h *= 0.1

x = np.arange(0, 3, 0.1)

## 没显示
d2l.plot(x, [f(x), 2 * x - 3], 'x', 'f(x)', legend=['f(x)', 'Tangent line (x=1)'])
