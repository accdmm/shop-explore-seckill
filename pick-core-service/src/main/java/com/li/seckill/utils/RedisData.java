package com.li.seckill.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: redis数据
 * @author: 李梦豪
 **/
@Data
public class RedisData {
    private LocalDateTime expireTime;
    private Object data;
}
