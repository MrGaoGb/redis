package com.xbqx.redis.stater;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.Gao
 * @date 2023/1/11 18:37
 * @apiNote:
 */
@Configuration
@ConditionalOnProperty(prefix = "redission", name = "enabled", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(value = RedisConfigProperties.class)
public class RedissonAutoConfiguration {



    @Bean
    public RedissonClient redissonClient(RedisConfigProperties redisConfigProperties) {
        Config config = new Config();
        String prefix = "redis://";
        config.useSingleServer()
                // "redis://192.168.1.148:6379"
                .setAddress(prefix + redisConfigProperties.getHost() + ":" + redisConfigProperties.getPort())
                .setTimeout(redisConfigProperties.getTimeout());
        return Redisson.create(config);
    }

}
