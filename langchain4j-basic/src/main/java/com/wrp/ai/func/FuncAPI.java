package com.wrp.ai.func;

import com.wrp.ai.service.Assistant;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ChatRequestParameters;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wrp
 * @since 2025-03-20 19:05
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("func")
public class FuncAPI {
    final ChatLanguageModel chatLanguageModel;
    final Assistant assistant;

    @GetMapping("low/chat")
    public String lowChat(@RequestParam String message) {
        ToolSpecification toolSpecification = ToolSpecification.builder()
                .name("com.wrp.ai.func.Calculator.calculator")
                .description("输入两个数，对这两个数求和")
                .parameters(JsonObjectSchema.builder()
                        .addIntegerProperty("a", "第一个参数")
                        .addIntegerProperty("b", "第二个参数")
                        .required("a", "b")
                        .build())
                .build();

        ChatResponse response = chatLanguageModel.chat(ChatRequest.builder()
                .messages(List.of(UserMessage.from(message)))
                .parameters(ChatRequestParameters.builder()
                        .toolSpecifications(toolSpecification)
                        .build())
                .build());

        response.aiMessage().toolExecutionRequests().forEach( toolExecutionRequest -> {
            System.out.println(toolExecutionRequest.name());
            System.out.println(toolExecutionRequest.arguments());

            // TODO 使用反射调用 方法
        });


        return response.aiMessage().text();
    }

    @GetMapping("high/chat")
    public String highChat(@RequestParam String message) {
        return assistant.chat(message);
    }

}
