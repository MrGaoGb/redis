package com.xbqx.mrgao.rabbitmq.producer.exchange;

import com.xbqx.mrgao.rabbitmq.config.HeadersExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/9/22 22:02
 */
@Slf4j
@Component
public class HeadersProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * x-match：any 属性任意匹配，就会被消费
     */
    public void send1() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("type", "1");
        messageProperties.setHeader("name", "sendQueue001");
        Message message = new Message("这是第一条消息".getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.send(HeadersExchangeConfig.HEADERS_EXCHANGE, "", message);
    }

    /**
     * x-match：all 所有属性必须全匹配才会被消费
     */
    public void send2() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("type", "2");
        messageProperties.setHeader("name", "sendQueue002");
        Message message = new Message("这是第二条消息".getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.send(HeadersExchangeConfig.HEADERS_EXCHANGE, "", message);
    }

    /**
     * x-match：all 所有属性必须全匹配才会被消费
     */
    public void send3() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("type", "3");
        // TODO 正确属性：为sendQueue003 ，此处传递错误值：sendQueue03 进行测试
        messageProperties.setHeader("name", "sendQueue03");
        Message message = new Message("这是第三条消息".getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.send(HeadersExchangeConfig.HEADERS_EXCHANGE, "", message);
    }
}

