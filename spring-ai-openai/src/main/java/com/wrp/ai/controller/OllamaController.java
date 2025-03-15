package com.wrp.ai.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wrp
 * @since 2025-03-13 23:01
 **/
@RestController
@RequestMapping("ollama")
public class OllamaController {

    @Resource
    OllamaChatModel ollamaChatModel;

    @GetMapping("chat")
    public String chat(@RequestParam String prompt) {
        return ollamaChatModel.call(prompt);
    }
}
