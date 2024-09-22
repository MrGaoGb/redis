package com.xbqx.mrgao.rabbitmq.consumer;

import com.xbqx.mrgao.rabbitmq.constants.RabbitMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/9/22 17:14
 */
@Slf4j
@Component
public class FanoutConsumer {


    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue("queue.topic.001"), // 消息队列名称
                    exchange = @Exchange(value = RabbitMQConstants.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT)// 交换机
                    , key = {"usa.*"} // 路由Routing KEY
            )})
    public void consumerProcess1(String message) {
        log.info(">>>>> (001队列) receive: {}", message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue("queue.topic.002"), // 消息队列名称
                    exchange = @Exchange(value = RabbitMQConstants.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT)// 交换机
                    , key = {"*.news"} // 路由Routing KEY
            )})
    public void consumerProcess2(String message) {
        log.info(">>>>> (002队列) receive: {}", message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue("queue.fanout.003"), // 消息队列名称
                    exchange = @Exchange(value = RabbitMQConstants.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT) // 交换机
                    , key = {"europe.*"} // 路由Routing KEY
            )})
    public void consumerProcess3(String message) {
        log.info(">>>>> (003队列) receive: {}", message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue("queue.fanout.004"), // 消息队列名称
                    exchange = @Exchange(value = RabbitMQConstants.FANOUT_EXCHANGE, type = ExchangeTypes.FANOUT) // 交换机
                    , key = {"*.weather"} // 路由Routing KEY
            )})
    public void consumerProcess4(String message) {
        log.info(">>>>> (004队列) receive: {}", message);
    }

}
