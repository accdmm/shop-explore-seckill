package com.li.seckill.dto;

import lombok.Data;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 登录-入参
 * @author: 李梦豪
 **/
@Data
public class LoginFormDTO {
    private String phone;
    private String code;
    private String password;
}
