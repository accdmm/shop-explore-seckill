package com.li.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.li.seckill.dto.CancelVoucherOrderDto;
import com.li.seckill.dto.GetVoucherOrderByVoucherIdDto;
import com.li.seckill.dto.GetVoucherOrderDto;
import com.li.seckill.dto.Result;
import com.li.seckill.entity.VoucherOrder;
import com.li.seckill.kafka.message.SeckillVoucherMessage;
import com.li.seckill.message.MessageExtend;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 优惠券订单 接口
 * @author: 李梦豪
 **/
public interface IVoucherOrderService extends IService<VoucherOrder> {

    Result<Long> seckillVoucher(Long voucherId);

    void createVoucherOrderV1(VoucherOrder voucherOrder);
    
    boolean createVoucherOrderV2(MessageExtend<SeckillVoucherMessage> message);
    
    Long getSeckillVoucherOrder(GetVoucherOrderDto getVoucherOrderDto);
    
    Boolean cancel(CancelVoucherOrderDto cancelVoucherOrderDto);
    
    boolean autoIssueVoucherToEarliestSubscriber(final Long voucherId, final Long excludeUserId);
    
    Long getSeckillVoucherOrderIdByVoucherId(GetVoucherOrderByVoucherIdDto getVoucherOrderByVoucherIdDto);
}
