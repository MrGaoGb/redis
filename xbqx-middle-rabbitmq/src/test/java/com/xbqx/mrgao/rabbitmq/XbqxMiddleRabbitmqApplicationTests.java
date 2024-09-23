package com.xbqx.mrgao.rabbitmq;

import com.xbqx.mrgao.rabbitmq.entity.OrderInf;
import com.xbqx.mrgao.rabbitmq.producer.exchange.DirectProducer;
import com.xbqx.mrgao.rabbitmq.producer.exchange.FanoutProducer;
import com.xbqx.mrgao.rabbitmq.producer.exchange.HeadersProducer;
import com.xbqx.mrgao.rabbitmq.producer.exchange.TopicProducer;
import com.xbqx.mrgao.rabbitmq.producer.messageconfirm.AutoProducer;
import com.xbqx.mrgao.rabbitmq.producer.messageconfirm.ManualProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest(classes = XbqxMiddleRabbitmqApplication.class)
@RunWith(value = SpringJUnit4ClassRunner.class)
public class XbqxMiddleRabbitmqApplicationTests {

    @Autowired
    private DirectProducer directProducer;

    /**
     * 案例描述：
     */
    @Test
    public void testDirectMessage() {
        directProducer.send();
    }


    @Autowired
    private TopicProducer topicProducer;

    /**
     * 案例描述：测试TopicExchange
     */
    @Test
    public void testTopicExchange() {
        topicProducer.send1();
        topicProducer.send2();
        topicProducer.send3();
        topicProducer.send4();
    }

    @Autowired
    private FanoutProducer fanoutProducer;

    /**
     * 案例描述：测试TopicExchange
     */
    @Test
    public void testFanoutExchange() {
        fanoutProducer.send1();
        fanoutProducer.send2();
        fanoutProducer.send3();
        fanoutProducer.send4();
    }


    @Autowired
    private HeadersProducer headersProducer;

    /**
     * 案例描述：HeaderExchange交换机
     */
    @Test
    public void testHeaderExchange() {
        headersProducer.send1();
        headersProducer.send2();
        headersProducer.send3();
    }

    @Autowired
    private ManualProducer manualProducer;

    @Test
    public void testManualConfirmMessage() {
        OrderInf o = new OrderInf();
        o.setId("0000000001");
        o.setType(1);
        o.setMoney(BigDecimal.TEN);
        o.setNow(LocalDateTime.now());
        manualProducer.sendConfirm(o);
    }

    @Autowired
    private AutoProducer autoProducer;

    @Test
    public void testAutoConfirmMessage() {
        autoProducer.sendAuto();
    }


    /**
     * 发布者确认：
     * <p>
     * TODO yml中配置：
     * spring.rabbitmq.publisher-confirm-type=correlated
     * spring.rabbitmq.template.mandatory=true
     * </p>
     */
    @Test
    public void testPublisherConfirmMessage() {
        autoProducer.sendAuto();
        autoProducer.sendAuto2();
    }
}
