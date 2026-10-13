package com.li.seckill.core;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 延迟队列 消费者接口
 * @author: 李梦豪
 **/
public interface ConsumerTask {
    
    void execute(String content);
  
    String topic();
}
