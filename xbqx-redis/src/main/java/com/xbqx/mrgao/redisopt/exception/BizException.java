package com.xbqx.mrgao.redisopt.exception;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * @author Mr.Gao
 * @date 2024/8/15 11:52
 * @apiNote:
 */
@Getter
public class BizException extends RuntimeException {

    private final String errCode;

    private final String errMsg;

    public BizException(String errCode, String errMsg) {
        super(errCode + "-" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BizException(ResponseCodeEnum responseCodeEnum, String errMsgTip) {
        this(responseCodeEnum.getCode(), StrUtil.format(responseCodeEnum.getDesc(), errMsgTip));
    }
}
