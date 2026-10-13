package com.li.seckill.util;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 分布式锁 方法类型执行 有返回值的业务
 * @author: 李梦豪
 **/
@FunctionalInterface
public interface TaskCall<V> {

    /**
     * 执行任务
     * @return 结果
     * */
    V call();
}
