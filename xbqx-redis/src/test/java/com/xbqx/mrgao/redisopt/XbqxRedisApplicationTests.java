package com.xbqx.mrgao.redisopt;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.scripting.support.ResourceScriptSource;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class XbqxRedisApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 案例描述：验证redisTemplate的setIfAbsent方法等同于SETNX
     */
    @Test
    public void testSetIfAbsent() {
        String key, value;

        // 设置SETNX命令
        key = fetchCacheKey();
        value = key + "a";
        Boolean success = setValue(key, value);
        System.out.println("第一次设置值，返回结果:" + success); // 结果为true

        // 第二次设置值
        value = key + "b";
        Boolean aBoolean = setValue(key, value);
        System.out.println("第二次设置值，返回结果:" + aBoolean); // 结果为false
    }

    /**
     * 案例描述：验证SETNX的过期时间
     */
    @Test
    public void testSetIfAbsentExpire() throws Exception {
        String key, value;

        // SETNX 设置有效期(第一次)
        key = fetchCacheKey();
        value = key + "c";
        Boolean c = setValue(key, value, 20);
        System.out.println("设置有效期(5s)，返回结果:" + c);

        // 在key相同的情况下：五秒内再次设置value值(有效期内)
        value = key + "d";
        Boolean d = setValue(key, value, 20);
        System.out.println("五秒内再次设置value值，返回结果：" + d);

        //延时20s(模拟key到期后的场景)
        TimeUnit.SECONDS.sleep(20);

        //在key相同的情况下：延时20s后再次设置值
        value = key + "e";
        Boolean e = setValue(key, value, 20);
        System.out.println("延时20s后再次设置value值，返回结果为：" + e);
    }

    /**
     * 案例描述：通过RedisCallback实现SETNX命令
     */
    @Test
    public void testRedisCallback() throws Exception {
        String key, value;

        // SETNX 设置有效期(第一次)
        key = fetchCacheKey();
        value = key + "c";
        Boolean c = setValueByRedisCallback(key, value, 20);
        System.out.println("设置有效期(5s)，返回结果:" + c);

        // 在key相同的情况下：五秒内再次设置value值(有效期内)
        value = key + "d";
        Boolean d = setValueByRedisCallback(key, value, 20);
        System.out.println("五秒内再次设置value值，返回结果：" + d);

        //延时20s(模拟key到期后的场景)
        TimeUnit.SECONDS.sleep(20);

        //在key相同的情况下：延时20s后再次设置值
        value = key + "e";
        Boolean e = setValueByRedisCallback(key, value, 20);
        System.out.println("延时20s后再次设置value值，返回结果为：" + e);
    }


    /**
     * 获取唯一Key
     *
     * @return
     */
    private String fetchCacheKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * SETNX 命令
     *
     * @param key
     * @param value
     * @return
     */
    private Boolean setValue(String key, Object value) {
        System.out.println("设置的key:" + key + ",设置的value:" + value);
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(key, value);
        if (Boolean.TRUE.equals(absent)) {
            System.out.println("当前KEY存在：" + key);
        }
        return absent;
    }

    /**
     * SETNX 命令
     *
     * @param key
     * @param value
     * @return
     */
    private Boolean setValue(String key, Object value, long expireSeconds) {
        System.out.println("设置的key:" + key + ",设置的value:" + value);
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(key, value, expireSeconds, TimeUnit.SECONDS);
        if (Boolean.TRUE.equals(absent)) {
            System.out.println("当前KEY存在：" + key);
        }
        return absent;
    }

    /**
     * 通过redisTemplate的execute方法RedisCallback自定义实现
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    private Boolean setValueByRedisCallback(String key, Object value, long expire) {
        System.out.println("设置的key:" + key + ",设置的value:" + value);
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            return connection.set(
                    key.getBytes(),
                    value.toString().getBytes(),
                    Expiration.from(expire, TimeUnit.SECONDS),
                    RedisStringCommands.SetOption.SET_IF_ABSENT
            );
        });
        //return redisTemplate.execute((RedisCallback<Boolean>) connection -> connection.set(key.getBytes(),value.toString().getBytes(),Expiration.from(expire,TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT));
    }

    private DefaultRedisScript<Long> redisScript = new DefaultRedisScript();

    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(("redis.lua"))));
    }

    /**
     * 运行redis lua脚本
     */
    @Test
    public void testRedisLua() {

        //List设置Lua的KEYS[1]
        String key = "ip:" + System.currentTimeMillis() / 1000;
        System.out.println(key);
        List<String> keyList = Arrays.asList("ip:1725077293");
        //List设置Lua的ARGV[1]
//        List<String> argvList = Arrays.asList();

        for (int i = 0; i < 30; i++) {
            Long execute = redisTemplate.execute(redisScript, keyList, 10, 5 * 60, TimeUnit.SECONDS);
            if (execute != null && execute == 0) {
                System.out.println("i:" + i + "(break)==>" + execute);
                break;
            }
            System.out.println("i:" + i + "==>" + execute);
        }

    }

    @Resource
    private RedissonClient redissonClient;

    /**
     * 案例描述：布隆过滤器实现之Redisson
     */
    @Test
    public void testBloomFilter() {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter("prd_key");
        bloomFilter.tryInit(1000, 0.03);

        for (int i = 0; i < 1000; i++) {
            bloomFilter.add("小玥玥" + i);
        }

        System.out.println("'小玥玥1'是否存在:" + bloomFilter.contains("小玥玥" + 1));
        System.out.println("'海贼王'是否存在:" + bloomFilter.contains("海贼王"));
        System.out.println("预计插入的数量:" + bloomFilter.getExpectedInsertions());
        System.out.println("容错率:" + bloomFilter.getFalseProbability());
        System.out.println("hash函数的个数:" + bloomFilter.getHashIterations());
        System.out.println("插入对象的个数:" + bloomFilter.count());

    }
}
