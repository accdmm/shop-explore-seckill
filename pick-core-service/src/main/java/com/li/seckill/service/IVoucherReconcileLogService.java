package com.li.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.li.seckill.dto.VoucherReconcileLogDto;
import com.li.seckill.entity.VoucherReconcileLog;
import com.li.seckill.kafka.message.SeckillVoucherMessage;
import com.li.seckill.message.MessageExtend;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 对账日志 接口
 * @author: 李梦豪
 **/
public interface IVoucherReconcileLogService extends IService<VoucherReconcileLog> {
    
    boolean saveReconcileLog(Integer logType,
                             Integer businessType,
                             String detail,
                             MessageExtend<SeckillVoucherMessage> message);
    
    boolean saveReconcileLog(Integer logType,
                             Integer businessType,
                             String detail,
                             Long traceId,
                             MessageExtend<SeckillVoucherMessage> message);
    
    
    boolean saveReconcileLog(VoucherReconcileLogDto voucherReconcileLogDto);
}