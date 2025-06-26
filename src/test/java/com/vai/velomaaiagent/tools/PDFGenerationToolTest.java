package com.vai.velomaaiagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author fwt
 * @date 2025/6/24
 * @Description
 */
@SpringBootTest
public class PDFGenerationToolTest {

    @Test
    public void testGeneratePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String fileName = "健身.pdf";
        String content = "这是个健身文档";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
    }
}
