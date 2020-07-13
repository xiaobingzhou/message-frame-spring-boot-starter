package com.github.xiaobingzhou.messageframe.autoconfigure;

import com.github.xiaobingzhou.messageframe.Dispatcher;
import com.github.xiaobingzhou.messageframe.DispatcherImpl;
import com.github.xiaobingzhou.messageframe.InitChecker;
import com.github.xiaobingzhou.messageframe.bind.BindParam;
import com.github.xiaobingzhou.messageframe.bind.impl.*;
import com.github.xiaobingzhou.messageframe.interceptor.ExecutionChain;
import com.github.xiaobingzhou.messageframe.interceptor.ExecutionChainImpl;
import com.github.xiaobingzhou.messageframe.interceptor.HandlerInterceptor;
import com.github.xiaobingzhou.messageframe.interceptor.support.ExposeHandlerRequestInterceptor;
import com.github.xiaobingzhou.messageframe.mapper.Mapper;
import com.github.xiaobingzhou.messageframe.matcher.Matcher;
import com.github.xiaobingzhou.messageframe.matcher.MatcherImpl;
import com.github.xiaobingzhou.messageframe.processor.RepositoryBeanPostProcessor;
import com.github.xiaobingzhou.messageframe.processor.SenderBeanPostProcessor;
import com.github.xiaobingzhou.messageframe.repository.BindParamRepository;
import com.github.xiaobingzhou.messageframe.repository.BodyCodecRepository;
import com.github.xiaobingzhou.messageframe.repository.HandlerRepository;
import com.github.xiaobingzhou.messageframe.repository.impl.BindParamRepositoryImpl;
import com.github.xiaobingzhou.messageframe.repository.impl.BodyCodecWithVersionRepositoryImpl;
import com.github.xiaobingzhou.messageframe.repository.impl.HandlerWithVersionRepositoryImpl;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MessageFrameConfigurationProperties.class)
public class MessageFrameAutoConfiguration {

    private MessageFrameConfigurationProperties properties;
    MessageFrameAutoConfiguration(MessageFrameConfigurationProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public Dispatcher dispatcher(ExecutionChain executionChain,
                                 HandlerRepository handlerRepository,
                                 BindParamRepository bindParamRepository) {

        DispatcherImpl dispatcher = new DispatcherImpl();
        dispatcher.setExecutionChain(executionChain);
        dispatcher.setHandlerRepository(handlerRepository);
        dispatcher.setBindParamRepository(bindParamRepository);

        return dispatcher;
    }

    @Bean
    @ConditionalOnMissingBean
    public BindParamRepository bindParamRepository() {
        return new BindParamRepositoryImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public Matcher matcher() {
        return new MatcherImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public HandlerRepository handlerRepository(Matcher matcher) {
        return new HandlerWithVersionRepositoryImpl(matcher);
    }

    @Bean
    @ConditionalOnMissingBean
    public BodyCodecRepository bodyCodecRepository(Matcher matcher) {
        return new BodyCodecWithVersionRepositoryImpl(matcher);
    }

    @Bean
    @ConditionalOnMissingBean
    public ExecutionChain executionChain() {
        return new ExecutionChainImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public BeanPostProcessor repositoryBeanPostProcessor(ExecutionChain executionChain,
                                                         HandlerRepository handlerRepository,
                                                         BindParamRepository bindParamRepository,
                                                         BodyCodecRepository bodyCodecRepository) {

        RepositoryBeanPostProcessor beanPostProcessor = new RepositoryBeanPostProcessor();
        beanPostProcessor.setExecutionChain(executionChain);
        beanPostProcessor.setHandlerRepository(handlerRepository);
        beanPostProcessor.setBindParamRepository(bindParamRepository);
        beanPostProcessor.setBodyCodecRepository(bodyCodecRepository);
        return beanPostProcessor;
    }

    @Bean
    @ConditionalOnMissingBean
    public BeanPostProcessor senderBeanPostProcessor(ExecutionChain executionChain) {
        SenderBeanPostProcessor beanPostProcessor = new SenderBeanPostProcessor();
        beanPostProcessor.setExecutionChain(executionChain);
        return beanPostProcessor;
    }

    @Bean
    @ConditionalOnMissingBean
    public InitChecker initChecker() {
        return new InitChecker();
    }

    @Bean
    @ConditionalOnMissingBean
    public DeviceIdBindParam deviceIdBindParam() {
        return new DeviceIdBindParam();
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageBindParam messageBindParam() {
        return new MessageBindParam();
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageFrameBindParam messageFrameBindParam() {
        return new MessageFrameBindParam();
    }

    @Bean
    @ConditionalOnMissingBean
    public BindParam requestBindParam() {
        return new RequestBindParam();
    }

    @Bean
    @ConditionalOnMissingBean
    public BindParam sysDateBindParam() {
        return new SysDateBindParam();
    }

    @Bean
    @ConditionalOnMissingBean
    public BindParam bodyJsonBindParam() {
        return new BodyJsonBindParam();
    }

    @Bean
    @ConditionalOnMissingBean
    public BindParam senderBindParam() {
        return new SenderBindParam();
    }

    @Bean
    @ConditionalOnMissingBean
    public Mapper mapper(BodyCodecRepository bodyCodecRepository) {
        return new Mapper(bodyCodecRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public HandlerInterceptor exposeHandlerRequestInterceptor() {
        return new ExposeHandlerRequestInterceptor();
    }
}
