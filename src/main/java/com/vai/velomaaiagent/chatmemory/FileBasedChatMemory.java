package com.vai.velomaaiagent.chatmemory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fwt
 * @date 2025/6/12
 * @Description
 */
public class FileBasedChatMemory implements ChatMemory {
    private final String BASE_DIR;
    private static final Kryo kryo = new Kryo();


    static {
        // 注册需要序列化的类
        kryo.setRegistrationRequired(false);
        // 设置实例化策略
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());

    }
    /**
     * 构造函数
     *
     * @param dir
     */
    public FileBasedChatMemory(String dir) {
        this.BASE_DIR = dir;
        File baseDir = new File(dir);
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
    }



    @Override
    public void add(String conversationId, Message message) {
        List<Message> conversation = getOrCreateConversation(conversationId);
        conversation.add(message);
        saveConversation(conversationId, conversation);
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        List<Message> conversation = getOrCreateConversation(conversationId);
        conversation.addAll(messages);
        saveConversation(conversationId, conversation);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        List<Message> conversation = getOrCreateConversation(conversationId);
        if (conversation.size() <= lastN) {
            return conversation;
        } else {
            return conversation.stream()
                    .skip(Math.max(0, conversation.size() - lastN))
                    .toList();
        }
    }

    @Override
    public void clear(String conversationId) {
        File conversationFile = getConversationFile(conversationId);
        if (conversationFile.exists()) {
            conversationFile.delete();
        }
    }
    /**
     * 获取会话
     *
     * @param conversationId
     * @return
     */
    public List<Message> getOrCreateConversation(String conversationId) {
        File conversationFile = getConversationFile(conversationId);
        List<Message> messages = new ArrayList<>();
        if(conversationFile.exists()){
            try (Input input = new Input(new FileInputStream(conversationFile))) {
                messages = kryo.readObject(input, ArrayList.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return messages;
    }
    /**
     * 保存会话
     */
    public void saveConversation(String conversationId, List<Message> messages) {
        File conversationFile = getConversationFile(conversationId);
        try (var output = new com.esotericsoftware.kryo.io.Output(new java.io.FileOutputStream(conversationFile))) {
            kryo.writeObject(output, messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取会话文件
     *
     * @param conversationId
     * @return
     */
    private File getConversationFile(String conversationId) {
        return new File(BASE_DIR, conversationId + ".kyro");
    }
}
