package com.wrp.ai.listener;

import dev.langchain4j.model.chat.listener.*;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;

import java.util.Map;

/**
 * @author wrp
 * @since 2025-03-19 20:52
 **/
public class MyChatModelListener implements ChatModelListener {
    @Override
    public void onRequest(ChatModelRequestContext requestContext) {
        ChatRequest request = requestContext.chatRequest();
        Map<Object, Object> attributes = requestContext.attributes();
        // 记录请求
        System.out.println("请求到达了");
    }

    @Override
    public void onResponse(ChatModelResponseContext responseContext) {
        ChatResponse response = responseContext.chatResponse();
        ChatRequest request = responseContext.chatRequest();
        Map<Object, Object> attributes = responseContext.attributes();
        // 记录响应
        System.out.println("请求响应了");
    }

    @Override
    public void onError(ChatModelErrorContext errorContext) {
        Throwable error = errorContext.error();
        ChatRequest request = errorContext.chatRequest();
        Map<Object, Object> attributes = errorContext.attributes();
        // 记录错误
        System.out.println("请求发生了错误");
    }
}
