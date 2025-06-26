package com.vai.velomaaiagent.rag;


import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;


/**
 * @author fwt
 * @date 2025/6/23
 * @Description  创建自定义空上下文查询增强工厂
 */
public class TrianAppContexualQueryAugmentFactory {
    public static ContextualQueryAugmenter createInstance(){
        PromptTemplate emptyPromptTemplate = new PromptTemplate("" +
                "你应该输入以下的内容:\n" +
                "抱歉我只能回答健身方面的知识" +
                "其他内容可以联系客服veloma0602@163.com");
        return ContextualQueryAugmenter.builder()
                .allowEmptyContext(false)
                .emptyContextPromptTemplate(emptyPromptTemplate)
                .build();
    }
}
