package com.xbqx.mrgao.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.Gao
 * @apiNote: HeadersExchange交换机配置
 * @date 2024/9/22 21:45
 */
@Configuration
public class HeadersExchangeConfig {

    public static final String HEADERS_QUEUE_1 = "queue.headers.001";
    public static final String HEADERS_QUEUE_2 = "queue.headers.002";
    public static final String HEADERS_QUEUE_3 = "queue.headers.003";
    public static final String HEADERS_EXCHANGE = "headersExchange";

    /**
     * 创建消息队列为：queue.headers.001
     *
     * @return
     */
    @Bean
    public Queue queue001() {
        return new Queue(HEADERS_QUEUE_1);
    }

    /**
     * 创建消息队列为：queue.headers.002
     *
     * @return
     */
    @Bean
    public Queue queue002() {
        return new Queue(HEADERS_QUEUE_2);
    }

    /**
     * 创建消息队列为：queue.headers.003
     *
     * @return
     */
    @Bean
    public Queue queue003() {
        return new Queue(HEADERS_QUEUE_3);
    }

    /**
     * 创建交换机: HeadersExchange
     *
     * @return
     */
    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADERS_EXCHANGE);
    }

    /**
     * 将队列Queue001和交换机HeadersExchange通过headerKeys进行绑定
     *
     * @return
     */
    @Bean
    public Binding binding001() {
        Map<String, Object> headerKeys = new HashMap<>(16);
        headerKeys.put("type", "1");
        headerKeys.put("name", "sendQueue001");
        return BindingBuilder.bind(queue001()).to(headersExchange()).whereAny(headerKeys).match();
    }

    /**
     * 将队列Queue002和交换机HeadersExchange通过headerKeys进行绑定
     *
     * @return
     */
    @Bean
    public Binding binding002() {
        Map<String, Object> headerKeys = new HashMap<>(16);
        headerKeys.put("type", "2");
        headerKeys.put("name", "sendQueue002");
        return BindingBuilder.bind(queue002()).to(headersExchange()).whereAll(headerKeys).match();
    }

    /**
     * 将队列Queue002和交换机HeadersExchange通过headerKeys进行绑定
     *
     * @return
     */
    @Bean
    public Binding binding003() {
        Map<String, Object> headerKeys = new HashMap<>(16);
        headerKeys.put("type", "3");
        headerKeys.put("name", "sendQueue003");
        return BindingBuilder.bind(queue003()).to(headersExchange()).whereAll(headerKeys).match();
    }
}
