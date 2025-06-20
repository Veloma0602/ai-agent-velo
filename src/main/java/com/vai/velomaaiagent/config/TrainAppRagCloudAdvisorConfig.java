package com.vai.velomaaiagent.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetriever;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrieverOptions;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fwt
 * @date 2025/6/16
 * @Description
 */
@Configuration
public class TrainAppRagCloudAdvisorConfig {
    @Value("${spring.ai.dashscope.api-key}")
    private String dashscopeApiKey;

    @Bean
    public RetrievalAugmentationAdvisor loadTrainAppRagCloudAdvisor() {
        DashScopeApi dashScopeApi = new DashScopeApi(dashscopeApiKey);
        final String KNOWLEDGE_INDEX = "健身增肌减脂知识库";
        DocumentRetriever dashScopeDocumentRetriever = new DashScopeDocumentRetriever(dashScopeApi, DashScopeDocumentRetrieverOptions.builder()
                .withIndexName(KNOWLEDGE_INDEX)
                .build());

        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(dashScopeDocumentRetriever)
                .build();
    }
}
