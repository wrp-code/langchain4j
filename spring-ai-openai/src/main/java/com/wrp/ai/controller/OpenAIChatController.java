//package com.wrp.ai.controller;
//
//import jakarta.annotation.Resource;
//import org.springframework.ai.openai.OpenAiChatModel;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Flux;
//
///**
// * @author wrp
// * @since 2025-03-13 22:21
// **/
//@RestController
//@RequestMapping("ai")
//public class OpenAIChatController {
//
//    @Resource
//    OpenAiChatModel openAiChatModel;
//
//    @GetMapping("chat")
//    public String chat(@RequestParam String message) {
//        return openAiChatModel.call(message);
//    }
//
//    @GetMapping("chat-stream")
//    public Flux<String> streamChat(@RequestParam String message) {
//        return openAiChatModel.stream(message);
//    }
//
//}
