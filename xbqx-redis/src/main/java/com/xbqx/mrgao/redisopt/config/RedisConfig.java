package com.xbqx.mrgao.redisopt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Mr.Gao
 * @date 2024/8/15 10:46
 * @apiNote:
 */
@Configuration
public class RedisConfig {

    /**
     * Redis的value序列化器
     *
     * @author suixince
     * @date 2021/1/31 20:44
     */
    @Bean
    public RedisSerializer<?> fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<>(Object.class);
    }

    //@Bean
    //public RedisSerializer<?> stringRedisSerializer() {
    //    return new StringRedisSerializer();
    //}


    /**
     * value是object类型的redis操作类
     *
     * @author suixince
     * @date 2021/1/31 20:45
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        //template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisRequestLockAspect redisRequestLockAspect(RedisTemplate<String, String> redisTemplate) {
        return new RedisRequestLockAspect(redisTemplate);
    }
}
