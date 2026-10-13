package com.li.seckill.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import com.li.seckill.cache.SeckillVoucherLocalCache;
import com.li.seckill.core.RedisKeyManage;
import com.li.seckill.entity.SeckillVoucher;
import com.li.seckill.entity.Voucher;
import com.li.seckill.handler.BloomFilterHandlerFactory;
import com.li.seckill.mapper.SeckillVoucherMapper;
import com.li.seckill.model.SeckillVoucherFullModel;
import com.li.seckill.redis.RedisCache;
import com.li.seckill.redis.RedisKeyBuild;
import com.li.seckill.service.ISeckillVoucherService;
import com.li.seckill.service.IVoucherService;
import com.li.seckill.servicelock.LockType;
import com.li.seckill.servicelock.annotion.ServiceLock;
import com.li.seckill.util.ServiceLockTool;
import org.redisson.api.RLock;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.li.seckill.constant.Constant.BLOOM_FILTER_HANDLER_VOUCHER;
import static com.li.seckill.constant.DistributedLockConstants.UPDATE_SECKILL_VOUCHER_LOCK;
import static com.li.seckill.constant.DistributedLockConstants.UPDATE_SECKILL_VOUCHER_STOCK_LOCK;
import static com.li.seckill.utils.RedisConstants.CACHE_NULL_TTL;
import static com.li.seckill.utils.RedisConstants.LOCK_SECKILL_VOUCHER_KEY;
import static com.li.seckill.utils.RedisConstants.LOCK_SECKILL_VOUCHER_STOCK_KEY;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 秒杀优惠券 接口实现
 * @author: 李梦豪
 **/
@Slf4j
@Service
public class SeckillVoucherServiceImpl extends ServiceImpl<SeckillVoucherMapper, SeckillVoucher> implements ISeckillVoucherService {
    
    @Resource
    private ServiceLockTool serviceLockTool;
    
    @Resource
    private RedisCache redisCache;
    
    @Resource
    private BloomFilterHandlerFactory bloomFilterHandlerFactory;

    @Resource
    private SeckillVoucherLocalCache seckillVoucherLocalCache;
    
    @Resource
    private SeckillVoucherMapper seckillVoucherMapper;
    
    @Resource
    private IVoucherService voucherService;
    
    @Override
    @ServiceLock(lockType= LockType.Read,name = UPDATE_SECKILL_VOUCHER_LOCK,keys = {"#voucherId"})
    public SeckillVoucherFullModel queryByVoucherId(Long voucherId) {
        RedisKeyBuild seckillVoucherRedisKey =
                RedisKeyBuild.createRedisKey(RedisKeyManage.SECKILL_VOUCHER_TAG_KEY, voucherId);
        RedisKeyBuild seckillVoucherNullRedisKey =
                RedisKeyBuild.createRedisKey(RedisKeyManage.SECKILL_VOUCHER_NULL_TAG_KEY, voucherId);
        SeckillVoucherFullModel localCacheHit = seckillVoucherLocalCache.get(seckillVoucherRedisKey.getRelKey());
        if (Objects.nonNull(localCacheHit)) {
            return localCacheHit;
        }
        SeckillVoucherFullModel seckillVoucherFullModel =
                redisCache.get(seckillVoucherRedisKey, SeckillVoucherFullModel.class);
        if (Objects.nonNull(seckillVoucherFullModel)) {
            seckillVoucherLocalCache.put(seckillVoucherRedisKey.getRelKey(), seckillVoucherFullModel);
            return seckillVoucherFullModel;
        }
        log.info("查询秒杀优惠券 从Redis缓存没有查询到 秒杀优惠券的优惠券id : {}",voucherId);
        if (!bloomFilterHandlerFactory.get(BLOOM_FILTER_HANDLER_VOUCHER).contains(String.valueOf(voucherId))) {
            log.info("查询秒杀优惠券 布隆过滤器判断不存在 秒杀优惠券id : {}",voucherId);
            throw new RuntimeException("查询秒杀优惠券不存在");
        }
        Boolean existResult = redisCache.hasKey(seckillVoucherNullRedisKey);
        if (existResult){
            throw new RuntimeException("查询秒杀优惠券不存在");
        }
        RLock lock = serviceLockTool.getLock(LockType.Reentrant, LOCK_SECKILL_VOUCHER_KEY, new String[]{String.valueOf(voucherId)});
        lock.lock();
        try {
            seckillVoucherFullModel = redisCache.get(seckillVoucherRedisKey, SeckillVoucherFullModel.class);
            if (Objects.nonNull(seckillVoucherFullModel)) {
                seckillVoucherLocalCache.put(seckillVoucherRedisKey.getRelKey(), seckillVoucherFullModel);
                return seckillVoucherFullModel;
            }
            existResult = redisCache.hasKey(seckillVoucherNullRedisKey);
            if (existResult){
                throw new RuntimeException("查询优惠券不存在");
            }
            SeckillVoucher seckillVoucher = lambdaQuery().eq(SeckillVoucher::getVoucherId,voucherId).one();
            if (Objects.isNull(seckillVoucher)) {
                redisCache.set(seckillVoucherNullRedisKey,
                        "这是一个空值",
                        CACHE_NULL_TTL,
                        TimeUnit.MINUTES);
                throw new RuntimeException("查询秒杀优惠券不存在");
            }
            long ttlSeconds = Math.max(
                    LocalDateTimeUtil.between(LocalDateTimeUtil.now(), seckillVoucher.getEndTime()).getSeconds(),
                    1L
            );
            Voucher voucher = voucherService.lambdaQuery().eq(Voucher::getId, voucherId).one();
            seckillVoucherFullModel = new SeckillVoucherFullModel();
            BeanUtils.copyProperties(seckillVoucher, seckillVoucherFullModel);
            seckillVoucherFullModel.setShopId(voucher.getShopId());
            seckillVoucherFullModel.setStatus(voucher.getStatus());
            seckillVoucherFullModel.setStock(null);
            redisCache.set(
                    seckillVoucherRedisKey,
                    seckillVoucherFullModel,
                    ttlSeconds,
                    TimeUnit.SECONDS
            );
            seckillVoucherLocalCache.put(seckillVoucherRedisKey.getRelKey(), seckillVoucherFullModel);
            return seckillVoucherFullModel;
        }finally {
            lock.unlock();
        }
    }
    
    @Override
    @ServiceLock(lockType= LockType.Read,name = UPDATE_SECKILL_VOUCHER_STOCK_LOCK,keys = {"#voucherId"})
    public void loadVoucherStock(Long voucherId){
        if (!bloomFilterHandlerFactory.get(BLOOM_FILTER_HANDLER_VOUCHER).contains(String.valueOf(voucherId))) {
            log.info("加载库存 布隆过滤器判断不存在 秒杀优惠券id : {}",voucherId);
            throw new RuntimeException("查询秒杀优惠券不存在");
        }
        String stock = 
                redisCache.get(RedisKeyBuild.createRedisKey(RedisKeyManage.SECKILL_STOCK_TAG_KEY, voucherId), String.class);
        if (Objects.nonNull(stock)) {
            return;
        }
        RLock lock = serviceLockTool.getLock(LockType.Reentrant, LOCK_SECKILL_VOUCHER_STOCK_KEY, 
                new String[]{String.valueOf(voucherId)});
        lock.lock();
        try {
            stock = redisCache.get(RedisKeyBuild.createRedisKey(RedisKeyManage.SECKILL_STOCK_TAG_KEY, voucherId), String.class);
            if (Objects.nonNull(stock)) {
                return;
            }
            SeckillVoucher seckillVoucher = lambdaQuery().eq(SeckillVoucher::getVoucherId,voucherId).one();
            if (Objects.nonNull(seckillVoucher)) {
                long ttlSeconds = Math.max(
                        LocalDateTimeUtil.between(LocalDateTimeUtil.now(), seckillVoucher.getEndTime()).getSeconds(),
                        1L
                );
                redisCache.set(
                        RedisKeyBuild.createRedisKey(RedisKeyManage.SECKILL_STOCK_TAG_KEY, voucherId),
                        String.valueOf(seckillVoucher.getStock()),
                        ttlSeconds,
                        TimeUnit.SECONDS
                );
            }
        }finally {
            lock.unlock();
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rollbackStock(final Long voucherId) {
        return seckillVoucherMapper.rollbackStock(voucherId) > 0;
    }
}
