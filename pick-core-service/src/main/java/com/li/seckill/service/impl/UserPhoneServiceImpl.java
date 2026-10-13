package com.li.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import com.li.seckill.entity.UserPhone;
import com.li.seckill.mapper.UserPhoneMapper;
import com.li.seckill.service.IUserPhoneService;
import org.springframework.stereotype.Service;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 用户手机 接口实现
 * @author: 李梦豪
 **/
@Slf4j
@Service
public class UserPhoneServiceImpl extends ServiceImpl<UserPhoneMapper, UserPhone> implements IUserPhoneService {
    
}
