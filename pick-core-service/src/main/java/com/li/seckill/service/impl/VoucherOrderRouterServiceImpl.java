package com.li.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import com.li.seckill.dto.GetVoucherOrderRouterDto;
import com.li.seckill.entity.VoucherOrderRouter;
import com.li.seckill.mapper.VoucherOrderRouterMapper;
import com.li.seckill.service.IVoucherOrderRouterService;
import com.li.seckill.utils.UserHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 优惠券订单路由实现 接口
 * @author: 李梦豪
 **/
@Slf4j
@Service
public class VoucherOrderRouterServiceImpl extends ServiceImpl<VoucherOrderRouterMapper, VoucherOrderRouter> implements IVoucherOrderRouterService {
    
    @Override
    public Long get(GetVoucherOrderRouterDto getVoucherOrderRouterDto) {
        VoucherOrderRouter voucherOrderRouter = lambdaQuery()
                .eq(VoucherOrderRouter::getUserId,  UserHolder.getUser().getId())
                .eq(VoucherOrderRouter::getVoucherId, getVoucherOrderRouterDto.getVoucherId())
                .one();
        if (Objects.nonNull(voucherOrderRouter)) {
            return voucherOrderRouter.getOrderId();
        }
        return null;
    }
}
