package com.xbqx.mrgao.rabbitmq;

import com.xbqx.mrgao.rabbitmq.producer.DirectProducer;
import com.xbqx.mrgao.rabbitmq.producer.FanoutProducer;
import com.xbqx.mrgao.rabbitmq.producer.HeadersProducer;
import com.xbqx.mrgao.rabbitmq.producer.TopicProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
}
