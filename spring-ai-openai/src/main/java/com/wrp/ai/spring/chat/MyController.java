package com.wrp.ai.spring.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author wrp
 * @since 2025年04月08日 10:14
 **/
@RestController("my")
public class MyController {
    private final ChatClient chatClient;

    public MyController(ChatClient.Builder builder) {
        this.chatClient = builder
                // 默认系统文本
                .defaultSystem("You are a friendly chat bot that answers question in the voice of a Pirate")
                .build();
    }

    @GetMapping("/ai")
    String generation(String userInput) {


        return this.chatClient.prompt()
                .user(userInput)
                .call()
                // 1. 直接返回content
//                .content();
                // 2. 返回完整的ChatResponse
//                .chatResponse()
//                .toString();
                // 3. 返回Entity
                .entity(String.class);
    }

    @GetMapping("/stream")
    Flux<String> generation() {
        return chatClient.prompt()
                .user("Tell me  a joke.")
                // 4. 流式
                .stream()
                .content();
    }
}
