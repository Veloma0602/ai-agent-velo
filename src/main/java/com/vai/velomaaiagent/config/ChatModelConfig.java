package com.vai.velomaaiagent.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author fwt
 * @date 2025/6/9
 * @Description
 */
@Configuration
@EnableConfigurationProperties
public class ChatModelConfig {
    @Bean
    @Primary
    @ConditionalOnProperty(name = "spring.ai.dashscope.chat.enabled", havingValue = "true")
    public DashScopeChatModel primaryChatModel(DashScopeChatModel dashScopeChatModel) {
        return dashScopeChatModel;
    }

    @Bean("ollamaChatModel")
    @ConditionalOnProperty(name = "spring.ai.ollama.chat.enabled", havingValue = "true")
    public OllamaChatModel ollamaChatModel(OllamaChatModel ollamaChatModel) {
        return ollamaChatModel;
    }
}
