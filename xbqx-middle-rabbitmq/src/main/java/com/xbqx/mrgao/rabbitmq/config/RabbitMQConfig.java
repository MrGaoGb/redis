package com.xbqx.mrgao.rabbitmq.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.Gao
 * @apiNote:MQ相关配置
 * @date 2024/9/22 16:04
 */
@Slf4j
@EnableRabbit
@Configuration
public class RabbitMQConfig {

    /**
     * 新增rabbitMQ的return 机制 和 confirm 机制
     *
     * @return
     * @author VilderLee
     */
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory rabbitConnectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        //确保消息发送到exchange中
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息成功发送到Exchange");
            } else {
                log.info("消息发送到Exchange失败, {}, cause: {}", correlationData, cause);
            }
        });

        //确保消息路由到queue中
        rabbitTemplate.setMandatory(true);

        // 该方法已被废弃
        //rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
        //    log.error("当前消息没有投递到Queue中！消息{}", JSONObject.toJSONString(message.getBody()));
        //    log.error("错误码为{}", replyCode);
        //    log.error("错误描述为{}", replyText);
        //    log.error("交换机为{}", exchange);
        //    log.error("路由Key为{}", routingKey);
        //});

        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            Message message = returnedMessage.getMessage();
            int replyCode = returnedMessage.getReplyCode();
            String replyText = returnedMessage.getReplyText();
            String exchange = returnedMessage.getExchange();
            String routingKey = returnedMessage.getRoutingKey();
            log.error("当前消息没有投递到Queue中！消息{}", JSONObject.toJSONString(message.getBody()));
            log.error("错误码为{}", replyCode);
            log.error("错误描述为{}", replyText);
            log.error("交换机为{}", exchange);
            log.error("路由Key为{}", routingKey);
        });

        return rabbitTemplate;
    }

}
