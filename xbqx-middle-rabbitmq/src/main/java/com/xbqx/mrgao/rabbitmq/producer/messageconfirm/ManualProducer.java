package com.xbqx.mrgao.rabbitmq.producer.messageconfirm;

import com.alibaba.fastjson.JSONObject;
import com.xbqx.mrgao.rabbitmq.entity.OrderInf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mr.Gao
 * @apiNote: 消息确认
 * @date 2024/9/23 11:25
 */
@Slf4j
@Component
public class ManualProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendConfirm(OrderInf orderInf) {
        rabbitTemplate.convertAndSend("directConfirmExchange",
                "confirm.rk",
                JSONObject.toJSONString(orderInf)
        );
    }
}
