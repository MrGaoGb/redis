package com.xbqx.mrgao.redisopt.exception;

/**
 * @author Mr.Gao
 * @date 2024/8/15 11:52
 * @apiNote:
 */
public class BizException extends RuntimeException {

    public BizException(ResponseCodeEnum responseCodeEnum, String errMsgTip) {
        super(String.format(responseCodeEnum.getDesc(), errMsgTip));
    }
}
