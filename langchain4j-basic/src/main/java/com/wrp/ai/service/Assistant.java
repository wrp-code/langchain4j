package com.wrp.ai.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * @author wrp
 * @since 2025-03-16 22:21
 **/
public interface Assistant {

    /**
     * 角色设置，对话
     */
    @SystemMessage("假如你是特朗普，接下来请以特朗普的语气来对话")
    String chat(String message);

    /**
     * 高级api，会话记忆
     */
    String chat(@MemoryId String memoryId, @UserMessage String message);
}
