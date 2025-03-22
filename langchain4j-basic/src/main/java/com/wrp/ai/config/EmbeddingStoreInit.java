package com.wrp.ai.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-03-19 21:46
 **/
@Configuration
@RequiredArgsConstructor
public class EmbeddingStoreInit {

    final PgConfig pgConfig;

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
                .dropTableFirst(false)
                .createTable(true)
                // 默认维度
                .dimension(384)
                .build();
    }
}
