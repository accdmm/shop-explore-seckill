package com.li.seckill.lockinfo.impl;

import com.li.seckill.lockinfo.AbstractLockInfoHandle;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 锁信息
 * @author: 李梦豪
 **/
public class ServiceLockInfoHandle extends AbstractLockInfoHandle {

    private static final String LOCK_PREFIX_NAME = "SERVICE_LOCK";
    
    @Override
    protected String getLockPrefixName() {
        return LOCK_PREFIX_NAME;
    }
}
