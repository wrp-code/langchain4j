from langchain_core.prompts import ChatPromptTemplate
from langchain_core.output_parsers import StrOutputParser

from langchain_openai import ChatOpenAI
# 需要设置openai key
llm = ChatOpenAI(openai_api_key='')

# 定义提示词模板
prompt = ChatPromptTemplate.from_messages([
    ("system", "你是世界级的技术专家"),
    ("user", "{input}")
])

# 创建一个字符串输出解析器
output_parser = StrOutputParser()

# | 将prompt和llm连接，组成一个langchain的链
chain = prompt | llm | output_parser

result = chain.invoke({"input": "帮我写一篇关于AI的技术文章,100个字"})
print(result)
