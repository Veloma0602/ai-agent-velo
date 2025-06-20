package com.vai.velomaaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author fwt
 * @date 2025/6/9
 * @Description
 */
@SpringBootTest
class TrainAppTest {
    @Resource
    private TrainApp trainApp;

    @Test
    void testChat(){
        String chatId = UUID.randomUUID().toString();
        //第一轮
        String message = "你好，我是方文涛";
        String answer = trainApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);

        //第二轮
        message = "我想要减脂";
        answer = trainApp.doChat(message,chatId);
        Assertions.assertNotNull(answer);

        message ="我叫什么来着，刚跟你说过，帮我回忆一下";
        answer = trainApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }


    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是方文涛,我想要进行减脂计划，但我不知道要怎么做";
        TrainApp.TrainReport  trainReport = trainApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(trainReport);
    }

    @Test
    void doRagChat() {
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是方文涛,我想要进行减脂计划，但是为什么减脂平台期迟迟不掉秤";
        String answer = trainApp.doRagChat(message, chatId);
        Assertions.assertNotNull(answer);
    }



}
