package com.vai.velomaaiagent.tools;

import cn.hutool.core.io.FileUtil;
import com.vai.velomaaiagent.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * @author fwt
 * @date 2025/6/24
 * @Description 文件操作工具类
 */
public class FileOperationTool {
    private  final String FILE_PATH = FileConstant.FILE_PATH + "/file"; // 文件路径

    @Tool(description = "Reads a file and returns its content")
    public String readFile(@ToolParam(description = "Name of the file to read") String fileName) {
        // 读取文件内容的逻辑
        String fielPath = FILE_PATH + "/" + fileName;
        // 这里可以使用Java的IO操作来读取文件内容
        try {
            return FileUtil.readUtf8String(fielPath);
        }catch (Exception e){
            return "Error reading file: " + e.getMessage();
        }
    }

    @Tool(description = "Writes content to a file")
    public String writeFile(@ToolParam(description = "Name of the file to write") String fileName,
                            @ToolParam(description = "Content to write to the file") String content) {
        // 写入文件内容的逻辑
        String filePath = FILE_PATH + "/" + fileName;
        try {
            FileUtil.mkdir(FILE_PATH);
            FileUtil.writeUtf8String(content, filePath);
            return "File written successfully"; // 返回写入成功的消息
        } catch (Exception e) {
            return "Error writing to file: " + e.getMessage();
        }
    }
}
