package com.xbqx.mrgao.rabbitmq.consumer.exchange;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Gao
 * @apiNote:Direct消费者
 * @date 2024/9/22 16:13
 */
@Slf4j
@Component
public class DirectConsumer {


    /**
     * 交换机绑定单个队列
     * <p>
     * 其中，@Queue("queue.test.001") 在项目启动时，会创建一个名为queue.test.001的队列。
     *
     * @Exchange("directExchange") 在项目启动时，会创建一个名为directExchange的交换机，如果不指定类型，默认就是direct exchange。
     * <p>
     * key指定交换机和队列的绑定的routing key。
     * <p>
     * 通过如此配置，交换机directExchange就通过routing key“rk.001”和队列queue.test.001绑定在一起了。
     * <p>
     * 一旦生产者向交换机directExchange发送消息，并指定routing key为“rk.001”，消费者consumer就可以消费消息了。
     */
    //@RabbitListener(bindings = {@QueueBinding(value = @Queue("queue.test.001"), // 消息队列名称
    //        exchange = @Exchange("directExchange"), // 交换机
    //        key = {"rk.001"} // 路由Routing KEY
    //)})
    public void consumerProcess001(String message) {
        log.info(">>>>> (001队列)receive: {}", message);
    }

    /**
     * 同一个交换机绑定002队列
     *
     * @param message
     */
    //@RabbitListener(bindings = {@QueueBinding(value = @Queue("queue.test.002"), // 消息队列名称
    //        exchange = @Exchange("directExchange"), // 交换机
    //        key = {"rk.001"} // 路由Routing KEY
    //)})
    public void consumerProcess002(String message) {
        log.info(">>>>> (002队列)receive: {}", message);
    }


}
