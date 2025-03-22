package com.wrp.ai.json;

import com.wrp.ai.service.Assistant;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.request.ChatRequestParameters;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.ResponseFormatType;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchema;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author wrp
 * @since 2025-03-20 20:24
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("json")
public class JsonAPI {
    final ChatLanguageModel chatLanguageModel;
    final Assistant assistant;

    @GetMapping("low/chat")
    public String lowChat(@RequestParam String message) {

        ResponseFormat responseFormat = ResponseFormat
                .builder()
                .type(ResponseFormatType.JSON)
                .jsonSchema(JsonSchema.builder()
                        .rootElement(JsonObjectSchema.builder()
                                .addStringProperty("name", "姓名")
                                .addIntegerProperty("age", "年龄")
                                .addStringProperty("gender", "性别")
                                .required("name", "age", "gender")
                                .build())
                        .build())
                .build();
        ChatResponse response = chatLanguageModel.chat(ChatRequest.builder()
                        .messages(List.of(UserMessage.from(message)))
                        .parameters(ChatRequestParameters.builder()
                                .responseFormat(responseFormat)
                                .build())
                .build());

        return response.aiMessage().text();
    }

    @GetMapping("high/chat")
    public String highChat(@RequestParam String message) {
        PersonService personService = AiServices.create(PersonService.class, chatLanguageModel);
        return personService.extract(message).toString();
    }

}

