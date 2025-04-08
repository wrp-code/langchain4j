//package com.wrp.ai.controller;
//
//import jakarta.annotation.Resource;
//import org.springframework.ai.image.Image;
//import org.springframework.ai.image.ImageGeneration;
//import org.springframework.ai.image.ImagePrompt;
//import org.springframework.ai.image.ImageResponse;
//import org.springframework.ai.openai.OpenAiImageModel;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author wrp
// * @since 2025-03-13 22:36
// **/
//@RestController
//@RequestMapping("image")
//public class OpenAIImageController {
//
//
//    @Resource
//    OpenAiImageModel openAiImageModel;
//
//    @GetMapping("create")
//    public String create(@RequestParam String prompt) {
//        ImageResponse response = openAiImageModel.call(new ImagePrompt(prompt));
//        ImageGeneration result = response.getResult();
//        Image output = result.getOutput();
//        String b64Json = output.getB64Json();
//        String url = output.getUrl();
//        return url;
//    }
//}
