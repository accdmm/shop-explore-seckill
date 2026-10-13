package com.li.seckill.service.impl;

import com.li.seckill.entity.ShopType;
import com.li.seckill.mapper.ShopTypeMapper;
import com.li.seckill.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 商铺类型 接口实现
 * @author: 李梦豪
 **/
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

}
