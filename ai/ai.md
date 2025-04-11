大模型 预训练模型

问题： 

1. 数据过时，使用插件，互联网查询
2. 无法查询私有化数据

解决方案

1. RAG

    数据（语料）大部分是文献资料、文档

2. SFT 微调

    问答、法律咨询、心理问诊



8B 模型微调

​	24G GPU内存 训练轮次 3-5次



LLMs应用落地核心技术

1. Prompting
2. RAG
    1. 缺点 检索出来的文档片段可能有问题（切割文档），使用重排序解决
    2. 缺点 对于系统延迟低场景
3. Fine-Turning
4. Pre-Training



Embeddings 文本向量

向量化工具必须要是同一个

距离计算：欧式距离、余弦距离





向量数据库

1. Chroma
2. Pinecone
3. Milvus
4. Weaviate
5. Qdrant

向量检索引擎

1. FAISS 
2. PGVector
3. RediSearch
4. ElasticSearch



构建索引库

1. load
2. split ,
    1. 简单文本处理，如langchain RecursiveCharacterTextSplitter
    2. 复杂文本处理
        1. 基于NLP篇章分析工具（discourse parsing），提取段落之间的主要关系，把所有包含主从关系的段落合并成一段
        2. 基于BERT中NSP训练任务（next sentence prediction）,设置相似度阈值，前后两段的相似度大于阈值则合并
3. embed
4. store

检索&生成

question -> 向量化 ->向量库中检索-> prompt（提示词模板，检索结果 + question） --> llm --> result





## transformer

类神经网络，上亿个参数

输入

- embedding

- 位置编码（RNN）





## 李宏毅AI导论


LLM 

GPT 

生成预训练Transform

文字接龙，概率模型



GPT ==> fineturn ==> ChatGPT

alignment 对齐人类需求

预训练

督导式学习 （人类指导）

增强式学习Reinforcement Learning （回馈） Reword Model



如何调整LLM

1. 改变自己（给更清楚的指令，提供更多的讯息） Prompt Engineering 
2. 改变模型 训练自己的模型



### 不训练模型，如何强化LLM
1. 咒语 
    1. Lets think step by step.
    2. 让模型解释一下自己的答案
    3. 对模型情绪勒索，这对我非常重要
    4. 直接问AI
2. 提供更多信息
3. 提供范例  in-context learning， 并未对模型进行训练
4. 拆解任务
    1. Chain of Thought CoT 模型思考或解释结论
5. 让模型检查自己的错误
6. 使用工具
    1. 搜索引擎 RAG
    2. 写程序
    3. 文生图（如，DALL-E）
7. 模型合作
    1. 让合适的模型做合适的事（如同策略模式）
    2. 让模型彼此讨论，需要裁判模型
    3. 多一点模型一起讨论，讨论多轮

XoT

- Chain of Thought
- Program of Thought
- Three of Thought

为什么同一个问题每次答案都不同？
- 文字接龙，几率分布

### 教导式学习
> 人类老师的教导 instruction Fine-tuning
>
> 需要耗费大量人力 => 获得资料标注
>
> 说明哪些是用户的说的，哪些是AI说的。

可以使用LLama3做为预训练的模型，得到初始参数。再根据其他AI逆向生成少量资料，进行Instruction FineTuning，获取专才AI模型

- 第一阶段，根据大量资料训练出来的模型，预训练，初始参数
- 第二阶段，根据人类少量资料（可以通过AI逆向工程获取），对第一阶段的参数进行调整出更好的参数 ，微调 fine tuning
    - adapter技术，不改变初始参数的情况下，增加少量参数，以得到微调的结果
    - 路线一，打造一堆专才
    - 路线二，直接打造一个通才

- 第三阶段， RLHF Reinforce learning from Human Feedback 增强式学习，给AI的不同答案打分，AI增加分数高的回答，降低分数低的回答
    - Reward Model 回馈模型，模拟人类打分；
    - 过度跟模拟人类学习，结果会更差

RLHF ==> RLAIF 让AI替代人类，对其他AI进行打分评价.

### AI Agent
> 让AI执行多步骤任务。
> - AutoGPT、AgentGPT、BabyAGI、Godmode
> - Figure 01
> - 有记忆的ChatGPT

### Transformer
> 类神经网络
> 
> Token Embedding（token和向量的对应关系是在训练时生成，没有考虑上下文）
1. Tokenization 文字转token
   - 准备一个token list（人为设置）
2. Input Layer 理解token
   - 每个token 转成向量 (embedding)(训练得到)
   - 为每一个位置设置一个独特的向量（positional Embedding）（训练/人为）
3. Attention 理解上下文
   - Contextualized Token Embedding
   - 找出相关的Token（计算相关性，训练）分数 Attention Weight
   - Attention Matrix
   - Causal Attention
   - Multi-head Attention
   - 计算时只考虑前面的Token（计算过的不在重复计算）
4. Feed Forward 整合、思考
   - 第三步和第四步组成Transformer Block，可以有多个
5. Output Layer 输出
   - 文字接龙生成答案
   - 答案挨个生成，生成的答案带回输入生成后续答案

### AI在想什么
> 人工智能是黑盒子，有些AI是开源的（知道参数和训练过程）
> - Transparency 透明的
> - Interpretable 思维是透明的
> - Explainable 可解释性

找出影响输出的关键输入，有以下方法
- 观察每一个输入的改变对结果的影响
- Gradient-based Approach
- 分析Attention
找出影响输出的关键训练资料
分析Embedding中存有什么样的信息
- Probing
- 将高维数据投影到二维平面上
- 用AI来解释AI（但也不一定可信他就是这样想的）
Surface词汇 ==> Syntactic语法 ==> Semantic语义

Sequence to sequence(seq2seq)
> input a sequence ==> Encoder ==> Decoder ==> output a sequence
> 
> 输出长度由模型决定
> 
> - seq2seq for text
> - seq2seq for Syntactic Parsing
> - seq2seq for Multi-label Classification
> - seq2seq for Object Detection