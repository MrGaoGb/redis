package com.xbqx.mrgao.rabbitmq.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Mr.Gao
 * @apiNote:RabbitMQ中的Confirm机制
 * @date 2024/9/23 17:00
 */
@Slf4j
@RestController
public class PublishConfirmController {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发布确认之同步确认
     * <p>
     * 优点：实现简单。
     * <p>
     * 缺点：由于是同步，效率低下。而且如果MQ服务不稳定，可能会导致业务线程一直阻塞，甚至会拖垮应用。
     * </p>
     *
     * @return
     */
    @GetMapping("/publish/syncConfirm")
    public String syncConfirm() {
        ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
        String result = "success";
        String msg = UUID.randomUUID().toString().replace("-", "");
        try (Connection connection = connectionFactory.createConnection(); Channel channel = connection.createChannel(false)) {
            // 开启当前信道上的confirm机制。
            channel.confirmSelect();

            // 将内容发布到 directAutoExchange（交换机）
            channel.basicPublish("directAutoExchange", "auto.rk001", null, msg.getBytes());

            // waitForConfirms()方法会阻塞,直到自上次调用以来发布的所有消息直到broker反馈了ack或nack
            boolean ack = channel.waitForConfirms();
            if (ack) {
                log.info("消息发送成功");
            } else {
                log.error("消息发送失败");
            }
        } catch (Exception e) {
            result = "fail";
            log.error("消息发送异常, 异常信息:{} 响应结果为:{}", e.getMessage(), result);
        }
        return result;
    }

    /**
     * 发布确认之批量确认
     * <p>
     * 优点：可以一次确认多条消息，效率比同步要好得多
     * <p>
     * 缺点：当一批数据中的某一条数据发送失败的情况下，就会导致这一批所有的数据都会ACK失败，如果处理不当，还很容易产生消息重复消费的问题。
     * </p>
     *
     * @return
     */
    @GetMapping("/publish/batchSyncConfirm")
    public String batchSyncConfirm() {
        ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
        String result = "success";
        List<String> batchMessages = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String msg = UUID.randomUUID().toString().replace("-", "");
            batchMessages.add(msg);
        }
        try (Connection connection = connectionFactory.createConnection(); Channel channel = connection.createChannel(false)) {
            // 开启当前信道上的confirm机制。
            channel.confirmSelect();

            // 将内容发布到 directAutoExchange（交换机）
            batchMessages.forEach(msg -> {
                try {
                    channel.basicPublish("directAutoExchange", "auto.rk001", null, msg.getBytes());
                } catch (IOException e) {
                    log.error("消息发送异常,异常原因:{}", e.getMessage());
                }
            });

            // waitForConfirms()方法会阻塞,直到自上次调用以来发布的所有消息直到broker反馈了ack或nack
            boolean ack = channel.waitForConfirms();
            if (ack) {
                log.info("==消息发送成功");
            } else {
                log.error("==消息发送失败");
            }
        } catch (Exception e) {
            result = "fail";
            log.error("消息发送异常, 异常信息:{} 响应结果为:{}", e.getMessage(), result);
        }
        return result;
    }


    /**
     * 发布确认之异步确认
     * <p>
     * 优点：吞吐量高，不用等待上一条消息返回ACK响应，就可以继续发送下一条消息。
     * <p>
     * 缺点：实现较上面两种比较复杂。
     * </p>
     *
     * @return
     */
    @GetMapping("/publish/asyncConfirm")
    public String asyncConfirm() {
        ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
        String result = "success";
        List<String> listMsg = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String msg = UUID.randomUUID().toString().replace("-", "");
            listMsg.add(msg);
        }
        try (Connection connection = connectionFactory.createConnection(); Channel channel = connection.createChannel(false)) {
            // 开启当前信道上的confirm机制。
            channel.confirmSelect();

            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    log.info("### 消息发生成功~~,deliveryTag = {}, multiple = {}!", deliveryTag, multiple);
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    log.error("消息发生失败");
                    log.info("deliveryTag = {}, multiple = {}", deliveryTag, multiple);
                }
            });

            // 将内容发布到 directAutoExchange（交换机）
            listMsg.forEach(msg -> {
                try {
                    channel.basicPublish("directAutoExchange", "auto.rk001", null, msg.getBytes());
                } catch (IOException e) {
                    log.error("消息投递失败,失败原因:{}", e.getMessage());
                }
            });

        } catch (Exception e) {
            result = "fail";
            log.error("消息发送异常, 异常信息:{} 响应结果为:{}", e.getMessage(), result);
        }
        return result;
    }

}
