package com.wrp.ai.memory;

import com.wrp.ai.service.Assistant;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wrp
 * @since 2025-03-16 22:39
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("api/memory")
public class MemoryAPI {

    final Assistant assistant;
    final ChatLanguageModel chatLanguageModel;
    private final ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
//    private final ChatMemory chatMemory = TokenWindowChatMemory.withMaxTokens()
    Map<String, ChatMemory> chatMemoryMap = new ConcurrentHashMap<>();

    @GetMapping("low/chat")
    public String lowChat(@RequestParam String message) {
        chatMemory.add(UserMessage.from(message));
        // 将所有的消息都给LLM
        ChatResponse response = chatLanguageModel.chat(chatMemory.messages());
        chatMemory.add(response.aiMessage());
        return response.aiMessage().text();
    }

    @GetMapping("high/chat")
    public String highChat(@RequestParam String memoryId, @RequestParam String message) {
        return assistant.chat(memoryId, message);
    }

    @GetMapping("high/chat/custom")
    public String highChatCustom(@RequestParam String memoryId, @RequestParam String message) {
        if(!chatMemoryMap.containsKey(memoryId)) {
            chatMemoryMap.put(memoryId, MessageWindowChatMemory.withMaxMessages(10));
        }
        ChatMemory chatMemory = chatMemoryMap.get(memoryId);
        // 将当前会话存储下
        chatMemory.add(UserMessage.from(message));
        // llm chat
        ChatResponse response = chatLanguageModel.chat(chatMemory.messages());
        // 存储响应
        chatMemory.add(response.aiMessage());
        // 返回
        return response.aiMessage().text();
    }
}
