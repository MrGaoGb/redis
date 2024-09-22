package com.xbqx.mrgao.rabbitmq.producer;

import com.xbqx.mrgao.rabbitmq.constants.RabbitMQConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/9/22 17:46
 */
@Component
public class FanoutProducer {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send1() {
        rabbitTemplate.convertAndSend(RabbitMQConstants.FANOUT_EXCHANGE, null, "这是一条usa新闻消息");
    }

    public void send2() {
        rabbitTemplate.convertAndSend(RabbitMQConstants.FANOUT_EXCHANGE, null, "这是一条usa天气消息");
    }

    public void send3() {
        rabbitTemplate.convertAndSend(RabbitMQConstants.FANOUT_EXCHANGE, null, "这是一条europe新闻消息");
    }

    public void send4() {
        rabbitTemplate.convertAndSend(RabbitMQConstants.FANOUT_EXCHANGE, null, "这是一条europe天气消息");
    }

}
