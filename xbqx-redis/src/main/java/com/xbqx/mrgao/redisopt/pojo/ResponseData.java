package com.xbqx.mrgao.redisopt.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2025/2/26 16:08
 */
@Data
public class ResponseData<T> implements Serializable {

    /**
     * 响应状态码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应对象
     */
    private T data;

    public ResponseData() {
    }

    public ResponseData(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseData(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>("0000", "success", data);
    }
}