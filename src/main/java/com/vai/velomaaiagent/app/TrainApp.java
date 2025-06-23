package com.vai.velomaaiagent.app;


import com.vai.velomaaiagent.advisor.MyLoggerAdvisor;
import com.vai.velomaaiagent.chatmemory.FileBasedChatMemory;
import com.vai.velomaaiagent.rag.QueryRewriter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * @author fwt
 * @date 2025/6/9
 * @Description
 */
@Component
public class TrainApp {

    private static final Logger log = LoggerFactory.getLogger(TrainApp.class);


    private final ChatClient chatClient;

    @Resource
    private VectorStore trainAppVectorStore;

    @Resource
    private QueryRewriter queryRewriter;

    private static final String SYSTEM_PROMPT = "你是一名融合运动科学和营养学知识的健身教练专家，具有ACE/NASM认证资质。" +
            "用户将通过你获取安全、科学、个性化的增肌/减脂解决方案。你的核心价值是：通过主动提问收集关键信息，为用户定制可执行的渐进式方案，" +
            "并在每次互动中建立信任感";
    @Autowired
    private RetrievalAugmentationAdvisor loadTrainAppRagCloudAdvisor;

    public TrainApp(ChatModel dashscopeChatModel){
        //初始化基于文件的对话记忆
        String fileDir = System.getProperty("user.dir") + "/chat_memory";
        ChatMemory chatMemory = new FileBasedChatMemory(fileDir);
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory)
                )
                .build();

    }

    /**
     * AI多轮对话聊天
     * @param message
     * @param chatId
     * @return
     */
    public String doChat(String message,String chatId){
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(
                        advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10)

                )
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}",content);
        return content;
    }
    record TrainReport(String userName,String title,String content){

    };

    /**
     * AI结构化输出聊天
     *
     * @param message
     * @return
     */

    public TrainReport doChatWithReport(String message, String chatId){
        TrainReport trainReport = chatClient
                .prompt()
                .system( SYSTEM_PROMPT + "每次对话都要生成一个健身减脂的计划结果，标题为{用户名}的健身减脂计划报告，内容为建议列表")
                .user(message)
                .advisors(
                        advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10)

                )
                .call()
                .entity(TrainReport.class);


        log.info("content: {}",trainReport);
        return trainReport;
    }

    /**
     * RAG（检索增强生成）聊天
     * @param message
     * @return
     *
     */
    public String doRagChat(String message, String chatId){
        String rewriteMessage = queryRewriter.doQueryRewrite(message);
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(rewriteMessage)
                .advisors(
                        advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10)
                )
                .advisors(new MyLoggerAdvisor())
                //检索增强顾问复杂版，用的云知识库
//                .advisors(loadTrainAppRagCloudAdvisor)
                //简单的问答顾问，使用向量存储进行检索
//                .advisors(new QuestionAnswerAdvisor(trainAppVectorStore))
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
        log.info("content: {}",content);
        return content;
    }
}
