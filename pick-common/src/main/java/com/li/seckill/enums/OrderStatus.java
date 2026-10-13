package com.li.seckill.enums;

import lombok.Getter;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 订单状态
 * @author: 李梦豪
 **/
public enum OrderStatus {
    /**
     * 订单状态
     * */
    NORMAL(1, "正常"),
    
    CANCEL(2, "取消"),
    ;
    
    @Getter
    private final Integer code;
    
    private String msg = "";
    
    OrderStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    
    public String getMsg() {
        return this.msg == null ? "" : this.msg;
    }
    
    public static String getMsg(Integer code) {
        for (OrderStatus re : OrderStatus.values()) {
            if (re.code.intValue() == code.intValue()) {
                return re.msg;
            }
        }
        return "";
    }
    
    public static OrderStatus getRc(Integer code) {
        for (OrderStatus re : OrderStatus.values()) {
            if (re.code.intValue() == code.intValue()) {
                return re;
            }
        }
        return null;
    }
}
