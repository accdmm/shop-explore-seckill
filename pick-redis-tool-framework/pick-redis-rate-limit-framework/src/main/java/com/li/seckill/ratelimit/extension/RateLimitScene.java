package com.li.seckill.ratelimit.extension;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 限流场景
 * @author: 李梦豪
 **/
public enum RateLimitScene {
    /** 发令牌接口 */
    ISSUE_TOKEN,
    /** 下单（秒杀）接口 */
    SECKILL_ORDER
}