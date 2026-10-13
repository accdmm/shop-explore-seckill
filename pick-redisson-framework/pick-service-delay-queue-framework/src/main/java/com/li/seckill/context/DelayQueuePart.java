package com.li.seckill.context;

import com.li.seckill.core.ConsumerTask;
import lombok.Data;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 消息主题
 * @author: 李梦豪
 **/
@Data
public class DelayQueuePart {
    
    private final DelayQueueBasePart delayQueueBasePart;
 
    private final ConsumerTask consumerTask;
    
    public DelayQueuePart(DelayQueueBasePart delayQueueBasePart, ConsumerTask consumerTask){
        this.delayQueueBasePart = delayQueueBasePart;
        this.consumerTask = consumerTask;
    }
}
