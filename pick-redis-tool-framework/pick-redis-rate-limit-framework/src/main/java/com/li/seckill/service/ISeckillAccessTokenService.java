package com.li.seckill.service;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 令牌
 * @author: 李梦豪
 **/
public interface ISeckillAccessTokenService {
  
    boolean isEnabled();
 
    String issueAccessToken(Long voucherId, Long userId);
    
    boolean validateAndConsume(Long voucherId, Long userId, String token);
}