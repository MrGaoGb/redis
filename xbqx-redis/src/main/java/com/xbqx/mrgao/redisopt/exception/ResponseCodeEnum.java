package com.xbqx.mrgao.redisopt.exception;

import lombok.Getter;

/**
 * @author Mr.Gao
 * @date 2024/8/15 11:52
 * @apiNote:
 */
@Getter
public enum ResponseCodeEnum {
    BIZ_CHECK_FAIL("A0001", "业务异常:{}");

    private final String code;
    private final String desc;

    ResponseCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
