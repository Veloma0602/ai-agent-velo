package com.vai.velomaaiagent.rag;

import cn.hutool.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fwt
 * @date 2025/6/16
 * @Description
 */
@Component

public class LoadAppDocumentLoader {

    private final Log log = Log.get(LoadAppDocumentLoader.class);

    private final  ResourcePatternResolver resourcePatternResolver;

    LoadAppDocumentLoader(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

    /**
     * 加载MARKDOWN文档
     */
    public List<Document> loadMarkdownDocuments(){
        List<Document> allDocuments = new ArrayList<>();
        try{
            Resource[] resources = resourcePatternResolver.getResources("classpath:documents/*.md");
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                        .withHorizontalRuleCreateDocument(true)
                        .withIncludeCodeBlock(false)
                        .withIncludeBlockquote(false)
                        .withAdditionalMetadata("filename", filename)
                        .build();
                MarkdownDocumentReader reader = new MarkdownDocumentReader(resource, config);
                allDocuments.addAll(reader.read());
            }
        } catch (IOException e) {
            log.error("Error loading markdown documents: " + e.getMessage());
        }
        return allDocuments;
    }
}
