package com.wrp.ai.config;

import com.wrp.ai.func.Calculator;
import com.wrp.ai.service.Assistant;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.web.search.WebSearchTool;
import dev.langchain4j.web.search.searchapi.SearchApiWebSearchEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-03-16 22:22
 **/
@Configuration
@RequiredArgsConstructor
public class AssistantInit {

    final ChatLanguageModel chatLanguageModel;

    /**
     * 基于内存的向量数据库
     */
//    @Bean
//    public EmbeddingStore<TextSegment> embeddingStore() {
//        return new InMemoryEmbeddingStore<>();
//    }

    @Bean
    public Assistant init(EmbeddingStore<TextSegment> embeddingStore,
                          SearchApiWebSearchEngine searchApiWebSearchEngine) {
        return AiServices.builder(Assistant.class)
                .chatLanguageModel(chatLanguageModel)
                // 对话记忆配置
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                // rag配置
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                // 注册functionCalling工具
                .tools(new Calculator(), new WebSearchTool(searchApiWebSearchEngine))
                .build();
    }
}
