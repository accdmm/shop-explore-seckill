package com.li.seckill.servicelock.annotion;

import com.li.seckill.servicelock.LockType;
import com.li.seckill.servicelock.info.LockTimeOutStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 注解
 * @author: 李梦豪
 **/
@Target(value= {ElementType.TYPE, ElementType.METHOD})
@Retention(value= RetentionPolicy.RUNTIME)
public @interface ServiceLock {
    
    LockType lockType() default LockType.Reentrant;
    
    String name() default "";
   
    String [] keys();
    
    long waitTime() default 10;
    
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    
    LockTimeOutStrategy lockTimeoutStrategy() default LockTimeOutStrategy.FAIL;
    
    String customLockTimeoutStrategy() default "";
}
