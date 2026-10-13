package com.li.seckill.servicelock.factory;

import com.li.seckill.core.ManageLocker;
import com.li.seckill.servicelock.LockType;
import com.li.seckill.servicelock.ServiceLocker;
import lombok.AllArgsConstructor;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 工厂
 * @author: 李梦豪
 **/
@AllArgsConstructor
public class ServiceLockFactory {
    
    private final ManageLocker manageLocker;
    

    public ServiceLocker getLock(LockType lockType){
        ServiceLocker lock;
        switch (lockType) {
            case Fair:
                lock = manageLocker.getFairLocker();
                break;
            case Write:
                lock = manageLocker.getWriteLocker();
                break;
            case Read:
                lock = manageLocker.getReadLocker();
                break;
            default:
                lock = manageLocker.getReentrantLocker();
                break;
        }
        return lock;
    }
}
