package com.li.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 优惠券订阅状态
 * @author: 李梦豪
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class GetSubscribeStatusVo implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    /**
     * 优惠券id
     * */
    private Long voucherId;
    
    /**
     * 是否订阅 1：已订阅  0：没有订阅
     * */
    private Integer subscribeStatus;
}
