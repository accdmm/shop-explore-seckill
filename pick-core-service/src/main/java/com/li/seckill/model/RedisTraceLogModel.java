package com.li.seckill.model;

import lombok.Data;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: redis 中的记录日志信息
 * @author: 李梦豪
 **/
@Data
public class RedisTraceLogModel {

    private String logType;
    
    private Long ts;
    
    private String orderId;
    
    private String traceId;
    
    private String userId;
    
    private String voucherId;
    
    private Integer beforeQty;
    
    private Integer changeQty;
    
    private Integer afterQty;
}
