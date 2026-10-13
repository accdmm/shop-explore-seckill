package com.li.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.li.seckill.entity.SeckillVoucher;
import com.li.seckill.model.SeckillVoucherFullModel;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 秒杀优惠券 接口
 * @author: 李梦豪
 **/
public interface ISeckillVoucherService extends IService<SeckillVoucher> {
    
    SeckillVoucherFullModel queryByVoucherId(Long voucherId);
    
    void loadVoucherStock(Long voucherId);
    
    boolean rollbackStock(Long voucherId);
}
