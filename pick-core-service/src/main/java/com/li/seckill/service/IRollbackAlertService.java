package com.li.seckill.service;

import com.li.seckill.entity.RollbackFailureLog;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 回滚失败通知服务：用于发送短信/邮件告警（可插拔实现）。
 * @author: 李梦豪
 **/
public interface IRollbackAlertService {

    void sendRollbackAlert(RollbackFailureLog log);
}