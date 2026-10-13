package com.li.seckill.context;

import com.li.seckill.config.DelayQueueProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.redisson.api.RedissonClient;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 延迟队列配置信息
 * @author: 李梦豪
 **/
@Data
@AllArgsConstructor
public class DelayQueueBasePart {
    
    private final RedissonClient redissonClient;
    
    private final DelayQueueProperties delayQueueProperties;
}
