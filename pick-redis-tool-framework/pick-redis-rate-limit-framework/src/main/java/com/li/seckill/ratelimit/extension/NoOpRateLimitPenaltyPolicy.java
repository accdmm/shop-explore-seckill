package com.li.seckill.ratelimit.extension;

import com.li.seckill.enums.BaseCode;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 默认空实现
 * @author: 李梦豪
 **/
public class NoOpRateLimitPenaltyPolicy implements RateLimitPenaltyPolicy {
    @Override
    public void apply(RateLimitContext ctx, BaseCode reason) {
        // no-op
    }
}