package com.li.seckill.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 获取优惠券订单路由
 * @author: 李梦豪
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class GetVoucherOrderRouterDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 优惠券id
     */
    @NotNull
    private Long voucherId;
}
