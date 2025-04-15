import torch
import torch.nn.functional as F
import numpy as np

inputs = np.array([[2.0, 1.0, 0.1]])

# 概率不变
temperature = 1
logits = torch.tensor(inputs / temperature)
softmax_scores = F.softmax(logits, dim=1)
print(f'temperature = {temperature} {softmax_scores.cpu().numpy()}')