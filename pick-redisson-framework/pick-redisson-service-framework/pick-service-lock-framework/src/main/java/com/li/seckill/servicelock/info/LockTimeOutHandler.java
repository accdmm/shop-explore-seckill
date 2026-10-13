package com.li.seckill.servicelock.info;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 处理失败抽象
 * @author: 李梦豪
 **/
public interface LockTimeOutHandler {
    
    /**
     * 处理
     * @param lockName 锁名
     * */
    void handler(String lockName);
}
