package com.li.seckill.core;

import com.li.seckill.servicelock.LockType;
import com.li.seckill.servicelock.ServiceLocker;
import com.li.seckill.servicelock.impl.RedissonFairLocker;
import com.li.seckill.servicelock.impl.RedissonReadLocker;
import com.li.seckill.servicelock.impl.RedissonReentrantLocker;
import com.li.seckill.servicelock.impl.RedissonWriteLocker;
import org.redisson.api.RedissonClient;

import java.util.HashMap;
import java.util.Map;

import static com.li.seckill.servicelock.LockType.Fair;
import static com.li.seckill.servicelock.LockType.Read;
import static com.li.seckill.servicelock.LockType.Reentrant;
import static com.li.seckill.servicelock.LockType.Write;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 缓存
 * @author: 李梦豪
 **/
public class ManageLocker {

    private final Map<LockType, ServiceLocker> cacheLocker = new HashMap<>();
    
    public ManageLocker(RedissonClient redissonClient){
        cacheLocker.put(Reentrant,new RedissonReentrantLocker(redissonClient));
        cacheLocker.put(Fair,new RedissonFairLocker(redissonClient));
        cacheLocker.put(Write,new RedissonWriteLocker(redissonClient));
        cacheLocker.put(Read,new RedissonReadLocker(redissonClient));
    }
    
    public ServiceLocker getReentrantLocker(){
        return cacheLocker.get(Reentrant);
    }
    
    public ServiceLocker getFairLocker(){
        return cacheLocker.get(Fair);
    }
    
    public ServiceLocker getWriteLocker(){
        return cacheLocker.get(Write);
    }
    
    public ServiceLocker getReadLocker(){
        return cacheLocker.get(Read);
    }
}
