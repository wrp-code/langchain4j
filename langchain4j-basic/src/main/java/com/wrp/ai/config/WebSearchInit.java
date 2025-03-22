package com.wrp.ai.config;

import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.searchapi.SearchApiWebSearchEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-03-20 20:05
 **/
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
