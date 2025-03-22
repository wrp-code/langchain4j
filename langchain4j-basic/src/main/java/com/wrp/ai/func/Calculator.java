package com.wrp.ai.func;

import dev.langchain4j.agent.tool.Tool;

/**
 * @author wrp
 * @since 2025-03-20 19:35
 **/
public class Calculator {


    @Tool("两数求和")
    public int add(int a, int b) {
        return a + b;
    }

    @Tool("两数相乘")
    public int multi(int a, int b) {
        return a * b;
    }
}
