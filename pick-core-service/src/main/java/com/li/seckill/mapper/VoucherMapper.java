package com.li.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.li.seckill.entity.Voucher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 优惠券 Mapper
 * @author: 李梦豪
 **/
public interface VoucherMapper extends BaseMapper<Voucher> {

    List<Voucher> queryVoucherOfShop(@Param("shopId") Long shopId);
}
