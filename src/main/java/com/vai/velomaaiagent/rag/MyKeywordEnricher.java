package com.vai.velomaaiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fwt
 * @date 2025/6/23
 * @Description 自定义关键词
 */
@Component
public class MyKeywordEnricher {
    @Resource
    private ChatModel dashscopeChatModel;

    List< Document> enrich(List<Document> documents){
        KeywordMetadataEnricher keywordMetadataEnricher = new KeywordMetadataEnricher(this.dashscopeChatModel, 5);
        return keywordMetadataEnricher.apply(documents);
    }
}


