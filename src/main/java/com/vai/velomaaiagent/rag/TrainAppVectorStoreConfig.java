package com.vai.velomaaiagent.rag;

import cn.hutool.log.Log;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fwt
 * @date 2025/6/16
 * @Description
 */
@Component
public class TrainAppVectorStoreConfig {
    private final Log log = Log.get(TrainAppVectorStoreConfig.class);

    @Resource
    private LoadAppDocumentLoader loadAppDocumentLoader;

//    @Bean
    VectorStore TrainAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        log.info("开始加载应用文档");
        SimpleVectorStore vectorStore =  SimpleVectorStore.builder(dashscopeEmbeddingModel)
                .build();
        List<Document> documents = loadAppDocumentLoader.loadMarkdownDocuments();
        vectorStore.add(documents);
        log.info("应用文档加载完成，向量存储已创建");
        return vectorStore;

    }
}
