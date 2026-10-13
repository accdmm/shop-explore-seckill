package com.li.seckill.lockinfo;

import org.aspectj.lang.JoinPoint;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 锁信息抽象
 * @author: 李梦豪
 **/
public interface LockInfoHandle {
   
    String getLockName(JoinPoint joinPoint, String name, String[] keys);
    
    String simpleGetLockName(String name,String[] keys);
}
