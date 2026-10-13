package com.li.seckill.servicelock.info;


/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 策略
 * @author: 李梦豪
 **/
public enum LockTimeOutStrategy implements LockTimeOutHandler{
    /**
     * 快速失败
     * */
    FAIL(){
        @Override
        public void handler(String lockName) {
            String msg = String.format("%s请求频繁",lockName);
            throw new RuntimeException(msg);
        }
    }
}
