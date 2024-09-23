package com.xbqx.mrgao.rabbitmq.consumer.messageconfirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/9/23 14:46
 */
@Slf4j
@Component
public class AutoConsumer {


    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue("queue.auto.001"),
                    exchange = @Exchange("directAutoExchange"),
                    key = {"auto.rk"}
            )
    })
    public void process(String message) {
        log.info("队列名称：queue.auto.001 收到的消息为:{}", message);
        // TODO 异常情况下 是否会重回队列当中 byzero
        int num = 100 / 0;
        System.out.println(num);
    }

    /**
     * 正常消费
     *
     * @param message
     */
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue("queue.auto.002"),
                    exchange = @Exchange("directAutoExchange"),
                    key = {"auto.rk001"}
            )
    })
    public void process1(String message) {
        log.info("队列名称：queue.auto.001 收到的消息为:{}", message);
    }

}
