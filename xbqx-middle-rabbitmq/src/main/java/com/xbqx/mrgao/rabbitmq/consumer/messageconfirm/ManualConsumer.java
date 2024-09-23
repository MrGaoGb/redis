package com.xbqx.mrgao.rabbitmq.consumer.messageconfirm;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.xbqx.mrgao.rabbitmq.entity.OrderInf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/9/23 13:59
 */
@Slf4j
@Component
public class ManualConsumer {

    /**
     * 正常消费
     *
     * @param message
     * @param deliveryTag
     * @param channel
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("queue.manual.001"),
                    exchange = @Exchange(value = "directConfirmExchange"),
                    key = {"confirm.rk"})
    })
    public void process(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        try {
            log.info(">>>>>>>>>>>>>>>> message :{}", message);
            log.info(">>>>>>>>>>>>>>>> deliveryTag:{}", deliveryTag);
            OrderInf orderInf = JSONObject.parseObject(message, OrderInf.class);
            log.info("转化成功后，得到的order信息:{}", JSONObject.toJSONString(orderInf));
            // 手动进行ack确认
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            // 消息消费失败，重入队列
            try {
                log.error("消息消费失败，重入队列...");
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException ex) {
                log.error("应答失败:", ex);
            }
        }
    }

    /**
     * 异常消费
     *
     * @param message
     * @param deliveryTag
     * @param channel
     */
    //@RabbitListener(bindings = {
    //        @QueueBinding(
    //                value = @Queue("queue.manual.001"),
    //                exchange = @Exchange(value = "directConfirmExchange"),
    //                key = {"confirm.rk"})
    //})
    //public void process2(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
    //    try {
    //        log.info(">>>>>>>>>>>>>>>> message :{}", message);
    //        log.info(">>>>>>>>>>>>>>>> deliveryTag:{}", deliveryTag);
    //        OrderInf orderInf = JSONObject.parseObject(message, OrderInf.class);
    //
    //        // TODO 异常消费，模拟消费异常之后消息重回队列中
    //        log.info(">>>>>>>>>>>>>>>> 订单Id:{}", orderInf.getId().substring(30, 40));
    //
    //        log.info("转化成功后，得到的order信息:{}", JSONObject.toJSONString(orderInf));
    //        // 手动进行ack确认
    //        channel.basicAck(deliveryTag, false);
    //    } catch (Exception e) {
    //        // 消息消费失败，重入队列
    //        try {
    //            log.error("消息消费失败，重入队列...");
    //            channel.basicNack(deliveryTag, false, true);
    //        } catch (IOException ex) {
    //            log.error("应答失败:", ex);
    //        }
    //    }
    //}

}
