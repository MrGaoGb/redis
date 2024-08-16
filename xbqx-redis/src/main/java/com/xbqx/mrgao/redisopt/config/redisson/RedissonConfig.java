package com.xbqx.mrgao.redisopt.config.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.Gao
 * @date 2024/8/15 13:57
 * @apiNote:分布式锁实现
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // 这里假设你使用单节点的Redis服务器
        config.useSingleServer()
                // 使用与Spring Data Redis相同的地址
                // redis://127.0.0.1:6379
                .setAddress("redis://" + host + ":" + port)
                // 如果有密码
                .setPassword(password)
                // 其他配置参数
                .setDatabase(0).setConnectionPoolSize(10).setConnectionMinimumIdleSize(2);
        // 创建RedissonClient实例
        return Redisson.create(config);
    }

}
