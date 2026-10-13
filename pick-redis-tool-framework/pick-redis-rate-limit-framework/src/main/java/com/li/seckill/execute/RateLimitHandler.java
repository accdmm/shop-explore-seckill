package com.li.seckill.execute;
import com.li.seckill.ratelimit.extension.RateLimitScene;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 限流执行 接口
 * @author: 李梦豪
 **/
public interface RateLimitHandler {
   
    void execute(Long voucherId, Long userId, RateLimitScene scene);
}
