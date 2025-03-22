package com.wrp.ai;

/**
 * @author wrp
 * @since 2025-03-19 20:44
 **/

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SimpleChatApplicationTest {

    @Autowired
    ChatLanguageModel chatLanguageModel;

    @Test
    public void testChat() {
        ChatResponse response = chatLanguageModel.chat(UserMessage.from("你好"));
        System.out.println(response.aiMessage().text());
    }
}
