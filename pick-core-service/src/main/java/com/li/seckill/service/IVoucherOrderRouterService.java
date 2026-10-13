package com.li.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.li.seckill.dto.GetVoucherOrderRouterDto;
import com.li.seckill.entity.VoucherOrderRouter;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 优惠券订单路由 接口
 * @author: 李梦豪
 **/
public interface IVoucherOrderRouterService extends IService<VoucherOrderRouter> {
    
    Long get(GetVoucherOrderRouterDto getVoucherOrderRouterDto);
}
