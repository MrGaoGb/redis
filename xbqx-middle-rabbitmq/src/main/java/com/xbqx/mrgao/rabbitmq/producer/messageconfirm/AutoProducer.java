package com.xbqx.mrgao.rabbitmq.producer.messageconfirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Mr.Gao
 * @apiNote: 消息确认之自动应答
 * @date 2024/9/23 14:44
 */
@Slf4j
@Component
public class AutoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendAuto() {
        String msg = UUID.randomUUID().toString().replace("-", "");
        rabbitTemplate.convertAndSend("directAutoExchange", "auto.rk", "AUTO_" + msg);
    }

    public void sendAuto2() {
        // TODO 此处routing key不存在
        rabbitTemplate.convertAndSend("directAutoExchange", "auto.rk002", "这是Auto消息确认下发送的消息2");
    }
}
