package com.xbqx.mrgao.rabbitmq.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Mr.Gao
 * @apiNote:
 * @date 2024/9/23 13:53
 */
@Data
public class OrderInf {

    private String id;

    private Integer type;

    private BigDecimal money;

    private LocalDateTime now;

}
