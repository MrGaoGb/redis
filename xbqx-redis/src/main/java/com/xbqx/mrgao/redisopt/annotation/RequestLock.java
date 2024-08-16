package com.xbqx.mrgao.redisopt.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.Gao
 * @date 2024/8/15 11:46
 * @apiNote:redis锁前缀、redis锁时间、redis锁时间单位、key分隔符
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestLock {

    /**
     * 过期时间 默认10s
     *
     * @return
     */
    long expire() default 10;

    /**
     * redis锁时间单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * redis锁前缀
     *
     * @return
     */
    String prefix() default "default";

    /**
     * key分隔符
     *
     * @return
     */
    String delimiter() default "&";

}
