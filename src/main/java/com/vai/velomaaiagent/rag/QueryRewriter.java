package com.vai.velomaaiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.stereotype.Component;

/**
 * @author fwt
 * @date 2025/6/23
 * @Description 查询重写
 */
@Component
public class QueryRewriter {
    private final QueryTransformer queryTransformer;

    public QueryRewriter(ChatModel dashscopeChatModel) {
        ChatClient.Builder builder = ChatClient.builder(dashscopeChatModel);
        //创建查询重写转换器
        queryTransformer =  RewriteQueryTransformer.builder()
                .chatClientBuilder(builder)
                .build();
    }

    public String doQueryRewrite(String prompt) {
        Query query = new Query(prompt);

        //执行查询重写
        Query transform = queryTransformer.transform(query);
        return transform.text();
    }
}
