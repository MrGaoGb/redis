package com.xbqx.mrgao.rabbitmq.consumer;

import com.xbqx.mrgao.rabbitmq.config.HeadersExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/9/22 22:30
 */
@Slf4j
@Component
public class HeaderConsumer {

    /**
     * 监听队列1
     *
     * @param message
     */
    @RabbitListener(queues = HeadersExchangeConfig.HEADERS_QUEUE_1)
    public void consumer1(String message) {
        log.info("消费队列:{} =>> 收到的消息为:{}", HeadersExchangeConfig.HEADERS_QUEUE_1, message);
    }

    /**
     * 监听队列2
     *
     * @param message
     */
    @RabbitListener(queues = HeadersExchangeConfig.HEADERS_QUEUE_2)
    public void consumer2(String message) {
        log.info("消费队列:{} =>> 收到的消息为:{}", HeadersExchangeConfig.HEADERS_QUEUE_2, message);
    }

    /**
     * 监听队列3
     *
     * @param message
     */
    @RabbitListener(queues = HeadersExchangeConfig.HEADERS_QUEUE_3)
    public void consumer3(String message) {
        log.info("消费队列:{} =>> 收到的消息为:{}", HeadersExchangeConfig.HEADERS_QUEUE_3, message);
    }

}
