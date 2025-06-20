package com.vai.velomaaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author fwt
 * @date 2025/6/5
 * @Description
 */
//@Component
public class SpringAiOllamaInvoke implements CommandLineRunner {

//    @Autowired
    @Qualifier("ollamaChatModel")
    private ChatModel ollamaModel;  // 如果需要使用Ollama模型

    @Override
    public void run(String... args) throws Exception {
        AssistantMessage assistantMessage = ollamaModel.call(new Prompt("你好"))
                .getResult()
                .getOutput();
        System.out.println(assistantMessage.getText());
    }
}
