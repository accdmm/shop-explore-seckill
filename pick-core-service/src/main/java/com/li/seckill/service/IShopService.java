package com.li.seckill.service;

import com.li.seckill.dto.Result;
import com.li.seckill.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 商铺 接口
 * @author: 李梦豪
 **/
public interface IShopService extends IService<Shop> {

    Result saveShop(Shop shop);
    
    Result queryById(Long id);

    Result update(Shop shop);

    Result queryShopByType(Integer typeId, Integer current, Double x, Double y);
}
