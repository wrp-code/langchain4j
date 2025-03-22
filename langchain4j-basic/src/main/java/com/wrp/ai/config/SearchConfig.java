package com.wrp.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wrp
 * @since 2025-03-16 21:45
 **/
@Configuration
@ConfigurationProperties(prefix = "search")
@Data
public class SearchConfig {
    private String engine;
    private String apiKey;
}
