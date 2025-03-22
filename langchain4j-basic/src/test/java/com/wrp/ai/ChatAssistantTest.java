package com.wrp.ai;

import com.wrp.ai.service.Assistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wrp
 * @since 2025-03-19 20:49
 **/
@SpringBootTest
public class ChatAssistantTest {

    @Autowired
    private Assistant assistant;

    @Test
    public void testChat() {
        String message = "Hello";
        String response = assistant.chat(message);
        System.out.println(response);
    }
}
