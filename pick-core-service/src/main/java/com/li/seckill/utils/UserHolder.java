package com.li.seckill.utils;

import com.li.seckill.dto.UserDTO;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 用户持有器-通用组件
 * @author: 李梦豪
 **/
public class UserHolder {
    private static final ThreadLocal<UserDTO> TL = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        TL.set(user);
    }

    public static UserDTO getUser(){
        return TL.get();
    }

    public static void removeUser(){
        TL.remove();
    }
}
