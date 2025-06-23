package com.vai.velomaaiagent.demo.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.rag.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author fwt
 * @date 2025/6/23
 * @Description
 */
class MultiQueryExpanderDemoTest {

    @Resource
    private MultiQueryExpanderDemo multiQueryExpander;

    @Test
    void expand() {
        List<Query> expandedQuery = multiQueryExpander.expand(new Query("如何使用spring ai?"));
        Assertions.assertNotNull(expandedQuery);
    }
}