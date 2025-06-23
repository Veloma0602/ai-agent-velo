package com.vai.velomaaiagent.rag;

import com.pgvector.PGvector;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgDistanceType.COSINE_DISTANCE;
import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgIndexType.HNSW;

/**
 * @author fwt
 * @date 2025/6/23
 * @Description PgVectorVectorStoreConfig
 */
@Configuration
public class PgVectorVectorStoreConfig {

//    @Bean
    public VectorStore pgVectorVectorStore(JdbcTemplate jdbcTemplate, EmbeddingModel dashscopeEmbeddingModel) {
        PgVectorStore vectorStore = PgVectorStore.builder(jdbcTemplate, dashscopeEmbeddingModel)
                .dimensions(1536)  // Optional: defaults to model dimensions or 1536
                .distanceType(COSINE_DISTANCE)      // Optional: defaults to COSINE_DISTANCE
                .indexType(HNSW)        // Optional: defaults to HNSW
                .initializeSchema(true)  // Optional: defaults to true, set to false if you want to manage schema manually
                .schemaName("public")   // Optional: defaults to "public"
                .vectorTableName("vector_store")
                .maxDocumentBatchSize(10000)
                .build();
        return vectorStore;
    }
}

