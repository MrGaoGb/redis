package com.xbqx.mrgao.rabbitmq.producer.exchange;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/9/22 17:08
 */
@Slf4j
@Component
public class TopicProducer {

    private static final String TOPIC_EXCHANGE = "topicExchange";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send1() {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, "usa.news", "这是一条usa新闻消息");
    }

    public void send2() {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, "usa.weather", "这是一条usa天气消息");
    }

    public void send3() {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, "europe.news", "这是一条europe新闻消息");
    }

    public void send4() {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, "europe.weather", "这是一条europe天气消息");
    }
}
