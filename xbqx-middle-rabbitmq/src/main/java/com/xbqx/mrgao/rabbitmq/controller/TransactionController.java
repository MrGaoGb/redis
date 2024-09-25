package com.xbqx.mrgao.rabbitmq.controller;

import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @Description RabbitMQ中的事务机制
 * @Author Mr.Gao
 * @Date 2024/9/25 23:54
 */
@Slf4j
@RestController
public class TransactionController {

    /**
     * 交换机名称
     */
    private static final String EXCHANGE_NAME = "directAutoExchange";
    /**
     * 路由KEY
     */
    private static final String ROUTING_KEY = "auto.rk001";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 事务机制
     *
     * @return
     */
    @SneakyThrows
    @GetMapping("/transaction/send")
    public String send() {
        String responseResult = "transactionSuccess";
        String msg = UUID.randomUUID().toString().replace("-", "");
        // 发送的内容
        String sendMessage = "事务消息:" + msg;
        Channel channel = null;
        try (Connection connection = rabbitTemplate.getConnectionFactory().createConnection()) {
            // 创建channel
            channel = connection.createChannel(true);

            // 开启事务
            channel.txSelect();

            log.info("向交换机:{} >> 路由键:{} 发送消息:{}", EXCHANGE_NAME, ROUTING_KEY, sendMessage);
            // 发送消息
            channel.basicPublish(EXCHANGE_NAME, // 交换机
                    ROUTING_KEY, // 路由键
                    null, sendMessage.getBytes(StandardCharsets.UTF_8) //发送的内容
            );

            // 提交事务
            channel.txCommit();

        } catch (Exception e) {
            if (channel != null) {
                // 回滚事务
                channel.txRollback();
            }
            e.printStackTrace();
            responseResult = "transactionFail";
        } finally {
            // 关闭资源
            if (channel != null) {
                channel.close();
            }
        }
        return responseResult;
    }

    /**
     * 事务机制_触发事务回滚
     *
     * @return
     */
    @SneakyThrows
    @GetMapping("/transaction/sendRollback")
    public String sendRollback() {
        String responseResult = "transactionSuccess";
        String msg = UUID.randomUUID().toString().replace("-", "");
        // 发送的内容
        String sendMessage = "事务消息:" + msg;
        Channel channel = null;
        try (Connection connection = rabbitTemplate.getConnectionFactory().createConnection()) {
            // 创建channel
            channel = connection.createChannel(true);

            // 开启事务
            channel.txSelect();

            log.info("向交换机:{} >> 路由键:{} 发送消息:{}", EXCHANGE_NAME, ROUTING_KEY, sendMessage);
            // 发送消息
            channel.basicPublish(EXCHANGE_NAME, // 交换机
                    ROUTING_KEY, // 路由键
                    null, sendMessage.getBytes(StandardCharsets.UTF_8) //发送的内容
            );

            // 触发异常 by zero
            int num = 1000 / 0;

            // 提交事务
            channel.txCommit();

        } catch (Exception e) {
            if (channel != null) {
                log.error("交换机:{} >> 路由键:{} 触发了事务回滚!", EXCHANGE_NAME, ROUTING_KEY);
                // 回滚事务
                channel.txRollback();
            }
            e.printStackTrace();
            responseResult = "transactionFail";
        } finally {
            // 关闭资源
            if (channel != null) {
                channel.close();
            }
        }
        return responseResult;
    }

}
