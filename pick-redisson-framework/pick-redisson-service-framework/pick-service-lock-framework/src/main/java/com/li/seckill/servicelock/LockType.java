package com.li.seckill.servicelock;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 分布式锁 锁类型
 * @author: 李梦豪
 **/
public enum LockType {
    /**
     * 锁类型
     */
    Reentrant,
    
    Fair,
   
    Read,
    
    Write;

    LockType() {
    }

}
