package com.li.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.li.seckill.dto.DelayVoucherReminderDto;
import com.li.seckill.dto.Result;
import com.li.seckill.dto.SeckillVoucherDto;
import com.li.seckill.dto.UpdateSeckillVoucherDto;
import com.li.seckill.dto.UpdateSeckillVoucherStockDto;
import com.li.seckill.dto.VoucherDto;
import com.li.seckill.dto.VoucherSubscribeBatchDto;
import com.li.seckill.dto.VoucherSubscribeDto;
import com.li.seckill.entity.Voucher;
import com.li.seckill.vo.GetSubscribeStatusVo;

import java.util.List;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 优惠券 接口
 * @author: 李梦豪
 **/
public interface IVoucherService extends IService<Voucher> {

    Long addVoucher(VoucherDto voucherDto);
    
    Result<List<Voucher>> queryVoucherOfShop(Long shopId);

    Long addSeckillVoucher(SeckillVoucherDto seckillVoucherDto);
    
    void updateSeckillVoucher(UpdateSeckillVoucherDto updateSeckillVoucherDto);
    
    void updateSeckillVoucherStock(UpdateSeckillVoucherStockDto updateSeckillVoucherDto);
    
    void subscribe(VoucherSubscribeDto voucherSubscribeDto);
    
    void unsubscribe(VoucherSubscribeDto voucherSubscribeDto);
    
    Integer getSubscribeStatus(VoucherSubscribeDto voucherSubscribeDto);
    
    List<GetSubscribeStatusVo> getSubscribeStatusBatch(VoucherSubscribeBatchDto voucherSubscribeBatchDto);
    
    void delayVoucherReminder(DelayVoucherReminderDto delayVoucherReminderDto);
}
