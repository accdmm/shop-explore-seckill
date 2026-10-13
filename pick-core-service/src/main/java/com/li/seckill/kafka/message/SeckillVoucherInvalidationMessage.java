package com.li.seckill.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 秒杀券缓存失效广播消息
 * @author: 李梦豪
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillVoucherInvalidationMessage {
    
    private Long voucherId;
    
    private String reason;
}