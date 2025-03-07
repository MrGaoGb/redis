package com.xbqx.mrgao.redisopt.controller;

import com.xbqx.mrgao.redisopt.annotation.RequestLock;
import com.xbqx.mrgao.redisopt.pojo.ResponseData;
import com.xbqx.mrgao.redisopt.pojo.req.LimitDto;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.Gao
 * @apiNote:请求控制器
 * @date 2025/2/26 15:32
 */
@RestController
@RequestMapping("/limit")
public class RequestController {

    /**
     * @return
     */
    @RequestLock(prefix = "reject")
    @PostMapping(value = "/rejectRepeatRequest", produces = "application/json")
    @ResponseBody
    public ResponseData<Object> rejectRepeatRequest(@RequestBody LimitDto limitDto) {
        return ResponseData.success("10000000000000000");
    }

    /**
     * 测试返回响应码
     */
    @PostMapping("/testVerifyRespCode")
    public void testVerifyRespCode() {
        System.out.println("测试返回响应码");
    }

}
