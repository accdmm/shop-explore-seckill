package com.li.seckill.lockinfo.impl;

import com.li.seckill.lockinfo.AbstractLockInfoHandle;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 锁信息
 * @author: 李梦豪
 **/
public class RepeatExecuteLimitLockInfoHandle extends AbstractLockInfoHandle {

    public static final String PREFIX_NAME = "REPEAT_EXECUTE_LIMIT";
    
    @Override
    protected String getLockPrefixName() {
        return PREFIX_NAME;
    }
}
