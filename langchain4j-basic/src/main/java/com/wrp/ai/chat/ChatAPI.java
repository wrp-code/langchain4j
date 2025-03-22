package com.wrp.ai.chat;

import com.wrp.ai.service.Assistant;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wrp
 * @since 2025-03-16 22:11
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class ChatAPI {

    final ChatLanguageModel chatLanguageModel;
    final Assistant assistant;

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

    @GetMapping("high/chat")
    public String highChat(@RequestParam String memoryId, @RequestParam String message) {
        return assistant.chat(memoryId, message);
    }
}
