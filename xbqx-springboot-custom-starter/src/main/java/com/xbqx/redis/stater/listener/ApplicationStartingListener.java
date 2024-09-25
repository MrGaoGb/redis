package com.xbqx.redis.stater.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Gao
 * @date 2023/2/16 17:38
 * @apiNote:表示应用在启动的时候做的事情
 */
@Slf4j
public class ApplicationStartingListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("ApplicationStartingListener ~~~~~~~~~~~启动中!!!!!");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            log.error("监听器,ApplicationStartingListener处理异常,异常原因:{}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("ApplicationStartingListener ~~~~~~~~~~~启动完成!!!!!");
    }
}