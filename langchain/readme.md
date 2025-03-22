## 一、llm-search

## 1.环境

LLM: GPT4o、配置系统的环境变量

- OPENAI_API_KEY=
- OPENAI_BASE_URL=

python 3.8+

## 2. 安装环境所需的库
```bash
pip install openai streamlit python-docx PyPDF2 faiss-cpu langchain langchain-core langchain-community langchain-openai
```

## 3. 运行

在配置中增加interpreter options `-m streamlit run -- `



## 二、RAG

### 环境

```bash
# 创建环境
conda create -n llamaindex-rag python=3.10

# 查看环境列表
conda env list

# 激活环境
conda activate llamaindex-rag

# 安装所需的依赖库
pip install -r requirements.txt
```

### 下载文本向量化处理模型，进行Embedding，选择Sentence Transformer

```python
# 模型下载
from modelscope import snapshot_download

# model_id 模型的id，在hugging face中进行查询
# cache_dir 缓存到本地的路径
model_dir = snapshot_download(model_id="sentence-transformers/paraphrase-multilingual-MiniLM-L12-v2",cache_dir="C:\AI\models")
```

- Embedding模型对文档召回率有较大影响。
- Xenova/text-embedding-ada-002 （openai）推荐处理多种语言

### 下载Qwen2.5-7B模型， LLM

```python
# 模型下载
from modelscope import snapshot_download

# model_id 模型的id，在hugging face中进行查询
# cache_dir 缓存到本地的路径
model_dir = snapshot_download(model_id="sentence-transformers/paraphrase-multilingual-MiniLM-L12-v2",cache_dir="C:\AI\models")
```

### HuggingFace运行模型

```python
from llama_index.llms.huggingface import HuggingFaceLLM
from llama_index.core.llms import ChatMessage

# 使用HuggingFace加载本地大模型
llm = HuggingFaceLLM(
    # 给定的是本地模型的全路径
	model_name=r"",
    tokenizer_name=r"",
    model_kwargs={"trust_remote_code":True},
    tokenizer_kwargs={"trust_remote_code":True}
)

rsp = llm.chat(messages=[ChatMessage(content="xtuner是什么？")]) # 微调工具
print(rsp)
```

如果有多张显卡，默认会同时工作，默认deviceMap=auto

### 创建知识库

```python



# 设置全局llm
Settings.llm = llm

#加载文档
documents = SimpleDirectoryReader("./data", required_exts=[".md"]).load_data()

# 创建VectorStoreIndex
index = VectorStoreIndex.from_documents(documents)

# 创建一个查询引擎
qurey_engine = index.as_query_engine()
response = query_engine.query("xtuner是什么？")

print(response)
```





- Langchain
- LlmaIndex 构建企业级私有知识库比Langchain效果更好
