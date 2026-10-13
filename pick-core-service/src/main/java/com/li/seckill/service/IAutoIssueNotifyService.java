package com.li.seckill.service;


/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 自动发券成功后的用户通知服务接口
 * @author: 李梦豪
 **/
public interface IAutoIssueNotifyService {
    
    void sendAutoIssueNotify(Long voucherId, Long userId, Long orderId);
}