package com.xbqx.mrgao.rabbitmq.producer.exchange;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Gao
 * @apiNote:生产者
 * @date 2024/9/22 16:08
 */
@Component
public class DirectProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     */
    public void send() {
        for (int i = 0; i < 10; i++) {
            String msg = String.format("这是第[%d]条消息", i);
            /**
             * @param1: 第一个指的是交换机名称:
             * @param2: routing key
             * @param3: 发送的消息
             */
            rabbitTemplate.convertAndSend("directExchange", "rk.001", msg);
        }
    }
}
