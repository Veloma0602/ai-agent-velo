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


    @Test
    void doChatWithTools() {
        // 测试联网搜索问题的答案
        testMessage("周末想带女朋友去上海约会，推荐几个适合情侣的小众打卡地？");

        // 测试网页抓取：健身案例分析
        testMessage(" 帮我抓取一下关于‘健身案例分析’的网页内容");

        // 测试资源下载：图片下载
        testMessage("直接下载一张适合做手机壁纸的星空情侣图片为文件");

        // 测试终端操作：执行代码
        testMessage("执行 Python3 脚本来生成数据分析报告");

        // 测试文件操作：保存用户档案
        testMessage("保存我的健身档案为文件");

        // 测试 PDF 生成
        testMessage("生成一份‘减脂计划’PDF，包含日常饮食安排，训练计划");
    }

    private void testMessage(String message) {
        String chatId = UUID.randomUUID().toString();
        String answer = trainApp.doToolChat(message, chatId);
        Assertions.assertNotNull(answer);
    }

}
