package com.li.seckill.config;

import com.li.seckill.constant.LockInfoType;
import com.li.seckill.handle.RedissonDataHandle;
import com.li.seckill.locallock.LocalLockCache;
import com.li.seckill.lockinfo.LockInfoHandle;
import com.li.seckill.lockinfo.factory.LockInfoHandleFactory;
import com.li.seckill.lockinfo.impl.RepeatExecuteLimitLockInfoHandle;
import com.li.seckill.repeatexecutelimit.aspect.RepeatExecuteLimitAspect;
import com.li.seckill.servicelock.factory.ServiceLockFactory;
import org.springframework.context.annotation.Bean;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 配置
 * @author: 李梦豪
 **/
public class RepeatExecuteLimitAutoConfiguration {
    
    @Bean(LockInfoType.REPEAT_EXECUTE_LIMIT)
    public LockInfoHandle repeatExecuteLimitHandle(){
        return new RepeatExecuteLimitLockInfoHandle();
    }
    
    @Bean
    public RepeatExecuteLimitAspect repeatExecuteLimitAspect(LocalLockCache localLockCache,
                                                             LockInfoHandleFactory lockInfoHandleFactory,
                                                             ServiceLockFactory serviceLockFactory,
                                                             RedissonDataHandle redissonDataHandle){
        return new RepeatExecuteLimitAspect(localLockCache, lockInfoHandleFactory,serviceLockFactory,redissonDataHandle);
    }
}
    