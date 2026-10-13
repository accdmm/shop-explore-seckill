package com.li.seckill.service;

import com.li.seckill.dto.Result;
import com.li.seckill.entity.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 关注接口
 * @author: 李梦豪
 **/
public interface IFollowService extends IService<Follow> {

    Result follow(Long followUserId, Boolean isFollow);

    Result isFollow(Long followUserId);

    Result followCommons(Long id);
}
