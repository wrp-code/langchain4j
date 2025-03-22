package com.wrp.ai.image;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

/**
 * @author wrp
 * @since 2025-03-20 20:57
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("image")
public class ImageAPI {

    final ChatLanguageModel chatLanguageModel;

    @GetMapping("low/chat")
    public String lowChat(@RequestParam String message) throws IOException {
        byte[] bytes = Files.readAllBytes(Path.of("D:\\file\\picture\\宠物\\_1040221.JPG"));
        return chatLanguageModel.chat(UserMessage.from(TextContent.from(message),
                ImageContent.from(Base64.getEncoder().encodeToString(bytes), "image/jpeg"))).aiMessage().text();
    }
}
