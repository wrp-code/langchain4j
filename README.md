# langchain4j
ai起步
## 1. 对话
1. 依赖
```xml
<dependencies>
    <dependency>
        <groupId>dev.langchain4j</groupId>
        <artifactId>langchain4j</artifactId>
        <version>${langchain4j.version}</version>
    </dependency>

    <dependency>
        <groupId>dev.langchain4j</groupId>
        <artifactId>langchain4j-community-dashscope-spring-boot-starter</artifactId>
        <version>${langchain4j.version}</version>
    </dependency>
</dependencies>
```
2. yml配置
```yaml
langchain4j:
# 灵积大模型
  community:
    dashscope:
      chat-model:
        api-key: ${DASHSCOPE_API_KEY}
#        model-name: deepseek-v3
        model-name: qwen-max-latest
```
3. api
```java
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ChatAPI {

    final ChatLanguageModel chatLanguageModel;

    @GetMapping("low/chat")
    public String lowChat(@RequestParam String message) {
        // SystemMessage场景设置
        return chatLanguageModel.chat(
                List.of(SystemMessage.systemMessage("假如你是特朗普，接下来请以特朗普的语气来对话"),
                        UserMessage.userMessage(message)))
                .aiMessage().text();
        // 简单ai对话
//        return chatLanguageModel.chat(UserMessage.from(message)).aiMessage().text();
    }
}
```

4. 高级api
```java
public interface Assistant {

    /**
     * 角色设置，对话
     */
    @SystemMessage("假如你是特朗普，接下来请以特朗普的语气来对话")
    String chat(String message);
}


@Bean
public Assistant init() {
    return AiServices.builder(Assistant.class)
            .chatLanguageModel(chatLanguageModel)
            .build();
}

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ChatAPI {

    final Assistant assistant;

    @GetMapping("high/chat")
    public String highChat(@RequestParam String memoryId, @RequestParam String message) {
        return assistant.chat(memoryId, message);
    }
}
```
## 2. 对话记忆
1. 对话记忆

2. 对话记忆持久化

## 3. RAG
1. 依赖引用
```xml
<!--内置了tika-->
    <dependency>
        <groupId>dev.langchain4j</groupId>
        <artifactId>langchain4j-easy-rag</artifactId>
        <version>${langchain4j.version}</version>
    </dependency>
```
2. rag in memory 配置
```java
@Configuration
@RequiredArgsConstructor
public class AssistantInit {

    final ChatLanguageModel chatLanguageModel;

    /**
     * 基于内存的向量数据库
     */
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }

    @Bean
    public Assistant init(EmbeddingStore<TextSegment> embeddingStore) {
        return AiServices.builder(Assistant.class)
                .chatLanguageModel(chatLanguageModel)
                // 对话记忆配置
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                // rag配置
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .build();
    }
}
```
3. api，加载文档，与对话
```java
@RestController
@RequiredArgsConstructor
@RequestMapping("rag")
public class RagAPI {

    final Assistant assistant;
    final EmbeddingStore<TextSegment> embeddingStore;

    @GetMapping("high/chat")
    public String highChat(@RequestParam String message) {
        return assistant.chat(message);
    }

    @GetMapping("load")
    public String loadDocument() {
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("D:\\temp");
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
        return "ok";
    }
}

```
### 3.1 EmbeddingModel

```xml
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j-embeddings</artifactId>
    <version>${langchain4j.version}</version>
</dependency>
```
切分文档
```java
// 默认使用
@GetMapping("load")
public String loadDocument() {
    List<Document> documents = FileSystemDocumentLoader.loadDocuments("D:\\temp");
    EmbeddingStoreIngestor.ingest(documents, embeddingStore);
    return "ok";
}

// 自定义切分规则
@GetMapping("load")
public String loadDocument() {
    List<Document> documents = FileSystemDocumentLoader.loadDocuments("D:\\temp");
    EmbeddingStoreIngestor ingester = EmbeddingStoreIngestor.builder()
            .embeddingModel(embeddingModel)
            .embeddingStore(embeddingStore)
            // maxSegmentSizeInChars 文档段的最大字符数
            // maxOverlapSizeInChars 文档段的最大重叠字符数
            .documentSplitter(new DocumentByLineSplitter(100, 40))
            .build();
    ingester.ingest(documents);
    return "ok";
}
```

### 3.2 向量数据库
1. 内置内存向量数据库
```java
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {
        return new InMemoryEmbeddingStore<>();
    }
```
2. Pgvector
> 需要安装pgvector扩展
> 
> 教程：https://github.com/pgvector/pgvector?tab=readme-ov-file

```xml
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j-pgvector</artifactId>
    <version>${langchain4j.version}</version>
</dependency>
```
配置pgvector
```java
    @Bean
    public EmbeddingStore<TextSegment> initEmbeddingStore() {
        return PgVectorEmbeddingStore
                .builder()
                .host(pgConfig.getHost())
                .port(pgConfig.getPort())
                .user(pgConfig.getUser())
                .password(pgConfig.getPassword())
                .database(pgConfig.getDatabase())
                .table(pgConfig.getTable())
                // 启动时删除表数据
                .dropTableFirst(true)
                .createTable(true)
                // 默认维度
                .dimension(384)
                .build();
    }
```

## 4. Function Calling
> 需要响应的大模型支持，否则报错：**{"error":"registry.ollama.ai/library/llama3:latest does not support tools"}**
1. @Tool 声明工具
```java
public class Calculator {


    @Tool("两数求和")
    public int add(int a, int b) {
        return a + b;
    }

    @Tool("两数相乘")
    public int multi(int a, int b) {
        return a * b;
    }
}
```
2. 注册工具
```java
    @Bean
    public Assistant init(EmbeddingStore<TextSegment> embeddingStore) {
        return AiServices.builder(Assistant.class)
                .chatLanguageModel(chatLanguageModel)
                // 注册functionCalling工具
                .tools(new Calculator())
                .build();
    }
```
3. 使用
```java
    @GetMapping("high/chat")
    public String highChat(@RequestParam String message) {
        return assistant.chat(message);
    }
```

## 5. 联网功能
> 本质也是一个function call
> 

1. 申请api key
> searchapi.io

2. 依赖
```xml
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j-web-search-engine-searchapi</artifactId>
    <version>${langchain4j.version}</version>
</dependency>
```
3. 配置
```yaml
search:
  api-key: ${SEARCH_API_KEY}
  engine: baidu
```
```java
@Configuration
@RequiredArgsConstructor
public class WebSearchInit {

    final SearchConfig searchConfig;

    @Bean
    public SearchApiWebSearchEngine webSearchEngine() {
        return SearchApiWebSearchEngine.builder()
                .engine(searchConfig.getEngine())
                .apiKey(searchConfig.getApiKey())
                .build();
    }
}
```
注册tool
```java
    @Bean
    public Assistant init(EmbeddingStore<TextSegment> embeddingStore,
                          SearchApiWebSearchEngine searchApiWebSearchEngine) {
        return AiServices.builder(Assistant.class)
                .chatLanguageModel(chatLanguageModel)
                // 注册functionCalling工具
                .tools(new Calculator(), new WebSearchTool(searchApiWebSearchEngine))
                .build();
    }
```
4. 正常使用对话

## 6. 结构化输出
低级api
```java
    @GetMapping("low/chat")
    public String lowChat(@RequestParam String message) {

        ResponseFormat responseFormat = ResponseFormat
                .builder()
                .type(ResponseFormatType.JSON)
                .jsonSchema(JsonSchema.builder()
                        .rootElement(JsonObjectSchema.builder()
                                .addStringProperty("name", "姓名")
                                .addIntegerProperty("age", "年龄")
                                .addStringProperty("gender", "性别")
                                .required("name", "age", "gender")
                                .build())
                        .build())
                .build();
        ChatResponse response = chatLanguageModel.chat(ChatRequest.builder()
                        .messages(List.of(UserMessage.from(message)))
                        .parameters(ChatRequestParameters.builder()
                                .responseFormat(responseFormat)
                                .build())
                .build());

        return response.aiMessage().text();
    }
```
高级api
```java
public record Person(String name, int age, String gender) { }

public interface PersonService {

    Person extract(String str);
}


    @GetMapping("high/chat")
    public String highChat(@RequestParam String message) {
        PersonService personService = AiServices.create(PersonService.class, chatLanguageModel);
        return personService.extract(message).toString();
    }
```

## 7. 多模态
> llava模型支持