package com.li.seckill.lua;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import com.li.seckill.redis.RedisCache;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.List;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 滑动
 * @author: 李梦豪
 **/
@Slf4j
public class SlidingRateLimitOperate {

    private final RedisCache redisCache;

    public SlidingRateLimitOperate(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    private DefaultRedisScript<Integer> redisScript;

    @PostConstruct
    public void init(){
        try {
            redisScript = new DefaultRedisScript<>();
            redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/rateLimitSliding.lua")));
            redisScript.setResultType(Integer.class);
        } catch (Exception e) {
            log.error("SlidingRateLimitOperate init lua error", e);
        }
    }

    public Long execute(List<String> keys, String[] args){
        return (Long)redisCache.getInstance().execute(redisScript, keys, args);
    }
}