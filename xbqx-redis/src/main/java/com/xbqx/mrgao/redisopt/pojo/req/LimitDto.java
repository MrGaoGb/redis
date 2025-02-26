package com.xbqx.mrgao.redisopt.pojo.req;

import com.xbqx.mrgao.redisopt.annotation.RequestKeyParam;
import lombok.Data;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/2/26 15:44
 */
@Data
public class LimitDto {

    @RequestKeyParam
    private String name;

    @RequestKeyParam
    private String password;
}
