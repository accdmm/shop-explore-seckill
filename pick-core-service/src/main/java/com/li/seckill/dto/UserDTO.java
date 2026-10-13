package com.li.seckill.dto;

import lombok.Data;
/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 用户-入参
 * @author: 李梦豪
 **/
@Data
public class UserDTO {
    private Long id;
    private String nickName;
    private String icon;
}
