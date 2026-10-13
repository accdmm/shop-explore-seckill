package com.li.seckill.util;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 分布式锁 方法类型执行 无返回值的业务
 * @author: 李梦豪
 **/
@FunctionalInterface
public interface TaskRun {
    
    /**
     * 执行任务
     * */
    void run();
}
