package com.vai.velomaaiagent.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author fwt
 * @date 2025/6/23
 * @Description 自定义分词
 */
@Component
public class MyTokenTextSplitter {
    public List<Document> split(List<Document> documents){
        TokenTextSplitter splitter = new TokenTextSplitter();
        return splitter.apply(documents);
    }

    public List<Document> splitCustomed(List< Document> doccuments){
        TokenTextSplitter splitter = new TokenTextSplitter(200, 100, 10, 5000, true);
        return splitter.apply(doccuments);
    }
}
