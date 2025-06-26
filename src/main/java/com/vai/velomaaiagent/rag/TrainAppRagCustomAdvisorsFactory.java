package com.vai.velomaaiagent.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;


/**
 * @author fwt
 * @date 2025/6/23
 * @Description 训练应用rag自定义顾问工厂
 */
@Slf4j
public class TrainAppRagCustomAdvisorsFactory {
    public static Advisor createTrainAppRagCustomAdvisors(VectorStore vectorStore, String status) {
        Filter.Expression expression = new FilterExpressionBuilder()
                .eq("status", status)
                .build();
        DocumentRetriever documentRetriever = VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStore) // 设置向量存储
                .filterExpression(expression) // 设置过滤条件
                .similarityThreshold(0.7) // 设置相似度阈值
                .topK(3) // 设置返回的文档数量
                .build();
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(documentRetriever)
                .queryAugmenter(TrianAppContexualQueryAugmentFactory.createInstance())
                .build();
    }
}
