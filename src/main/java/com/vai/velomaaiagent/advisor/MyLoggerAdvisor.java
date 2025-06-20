package com.vai.velomaaiagent.advisor;

/**
 * @author fwt
 * @date 2025/6/9
 * @Description
 */

import com.alibaba.nacos.shaded.io.grpc.netty.shaded.io.netty.handler.codec.MessageAggregator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.*;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.model.*;
import reactor.core.publisher.Flux;




/**
 * 自定义日志 Advisor
 * 打印 info 级别日志、只输出单次用户提示词和 AI 回复的文本
 */

public class MyLoggerAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {
    private static final Logger log = LoggerFactory.getLogger(MyLoggerAdvisor.class);

    private static final int MAX_CONTENT_LENGTH = 1024 * 1024;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private AdvisedRequest before(AdvisedRequest request) {
        log.info("AI Request: {}", request.userText());
        return request;
    }

    private void observeAfter(AdvisedResponse advisedResponse) {
        log.info("AI Response: {}", advisedResponse.response().getResult().getOutput().getText());
    }

    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        advisedRequest = this.before(advisedRequest);
        Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);
        return advisedResponses.doOnNext(this::observeAfter);
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        advisedRequest = this.before(advisedRequest);
        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
        this.observeAfter(advisedResponse);
        return advisedResponse;
    }
}

