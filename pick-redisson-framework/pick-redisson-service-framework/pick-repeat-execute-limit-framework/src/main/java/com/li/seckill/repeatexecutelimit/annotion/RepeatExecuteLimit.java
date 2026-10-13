package com.li.seckill.repeatexecutelimit.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 注解
 * @author: 李梦豪
 **/
@Target(value= {ElementType.TYPE, ElementType.METHOD})
@Retention(value= RetentionPolicy.RUNTIME)
public @interface RepeatExecuteLimit {
    
    String name() default "";
   
    String [] keys();
    
    long durationTime() default 0L;
    
    String message() default "提交频繁，请稍后重试";
    
}
