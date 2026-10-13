package com.li.seckill.service;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 对账执行 接口
 * @author: 李梦豪
 **/
public interface IReconciliationTaskService {
    
    void reconciliationTaskExecute();

    /**
     * 删除指定券的 Redis 库存键，触发按需重载。
     */
    void delRedisStock(Long voucherId);
}
