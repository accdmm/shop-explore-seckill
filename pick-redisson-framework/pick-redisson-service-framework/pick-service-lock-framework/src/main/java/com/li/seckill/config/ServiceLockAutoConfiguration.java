package com.li.seckill.config;

import com.li.seckill.constant.LockInfoType;
import com.li.seckill.core.ManageLocker;
import com.li.seckill.lockinfo.LockInfoHandle;
import com.li.seckill.lockinfo.factory.LockInfoHandleFactory;
import com.li.seckill.lockinfo.impl.ServiceLockInfoHandle;
import com.li.seckill.servicelock.aspect.ServiceLockAspect;
import com.li.seckill.servicelock.factory.ServiceLockFactory;
import com.li.seckill.util.ServiceLockTool;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 配置
 * @author: 李梦豪
 **/
public class ServiceLockAutoConfiguration {
    
    @Bean(LockInfoType.SERVICE_LOCK)
    public LockInfoHandle serviceLockInfoHandle(){
        return new ServiceLockInfoHandle();
    }
    
    @Bean
    public ManageLocker manageLocker(RedissonClient redissonClient){
        return new ManageLocker(redissonClient);
    }
    
    @Bean
    public ServiceLockFactory serviceLockFactory(ManageLocker manageLocker){
        return new ServiceLockFactory(manageLocker);
    }
    
    @Bean
    public ServiceLockAspect serviceLockAspect(LockInfoHandleFactory lockInfoHandleFactory,ServiceLockFactory serviceLockFactory){
        return new ServiceLockAspect(lockInfoHandleFactory,serviceLockFactory);
    }
    
    @Bean
    public ServiceLockTool serviceLockTooL(LockInfoHandleFactory lockInfoHandleFactory,ServiceLockFactory serviceLockFactory){
        return new ServiceLockTool(lockInfoHandleFactory,serviceLockFactory);
    }
}
