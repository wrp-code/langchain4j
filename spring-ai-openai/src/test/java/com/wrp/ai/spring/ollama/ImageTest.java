package com.wrp.ai.spring.ollama;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * @author wrp
 * @since 2025年04月08日 14:17
 **/
@SpringBootTest
public class ImageTest {

    @Resource
    ChatModel chatModel;

    @Test
    public void test() {
        var imageResource = new ClassPathResource("/multimodal.test.png");
        var userMessage = new UserMessage("解释一下你在这张照片中看到了什么，用中文回答",
                new Media(MimeTypeUtils.IMAGE_PNG, imageResource));
        ChatResponse response = chatModel.call(new Prompt(userMessage,
                OllamaOptions.builder().model(OllamaModel.LLAVA).build()));
        System.out.println(response.getResult().getOutput().getText());
    }

    @Test
    public void test2() throws IOException {
        var userMessage = new UserMessage("生成一张道德经第一章的高清壁纸图片，格式为png");
        ChatResponse response = chatModel.call(new Prompt(userMessage,
                OllamaOptions.builder().model(OllamaModel.LLAMA3_2_VISION_11b).build()));
        // 将结果保存到本地
        try {
            // 假设响应中包含图片的字节数据
            byte[] imageData = response.getResult().getOutput().getMedia().getFirst().getDataAsByteArray(); // 这个方法取决于你的ChatResponse实现

            // 定义保存路径
            Path outputPath = Path.of("道德经第一章壁纸.png");

            // 写入文件
            Files.write(outputPath, imageData, StandardOpenOption.CREATE);

            System.out.println("图片已保存到: " + outputPath.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("保存图片时出错: " + e.getMessage());
        }
    }
}
