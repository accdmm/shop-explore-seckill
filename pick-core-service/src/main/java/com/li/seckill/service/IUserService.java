package com.li.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.li.seckill.dto.LoginFormDTO;
import com.li.seckill.dto.Result;
import com.li.seckill.entity.User;
import jakarta.servlet.http.HttpSession;


/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 用户 接口
 * @author: 李梦豪
 **/
public interface IUserService extends IService<User> {

    Result<String> sendCode(String phone, HttpSession session);

    Result<String> login(LoginFormDTO loginForm, HttpSession session);

    Result<Void> sign();

    Result<Integer> signCount();

}
