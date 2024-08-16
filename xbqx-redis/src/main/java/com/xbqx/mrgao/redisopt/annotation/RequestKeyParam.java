package com.xbqx.mrgao.redisopt.annotation;

/**
 * @author Mr.Gao
 * @date 2024/8/15 11:44
 * @apiNote:
 */

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestKeyParam {
}
