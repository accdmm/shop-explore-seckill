package com.li.seckill.constant;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 分布式锁 业务名管理
 * @author: 李梦豪
 **/
public class DistributedLockConstants {
    
    public final static String UPDATE_USER_INFO_LOCK = "h_update_user_info_lock";
    
    public final static String UPDATE_SECKILL_VOUCHER_LOCK = "h_update_seckill_voucher_lock";
    
    public final static String UPDATE_SECKILL_VOUCHER_STOCK_LOCK = "h_update_seckill_voucher_stock_lock";
}
