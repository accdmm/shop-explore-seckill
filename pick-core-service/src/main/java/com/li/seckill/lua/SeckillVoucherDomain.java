package com.li.seckill.lua;

import lombok.Data;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: lua秒杀返回数据
 * @author: 李梦豪
 **/
@Data
public class SeckillVoucherDomain {

    private Integer code;
    
    private Integer beforeQty;
    
    private Integer deductQty;
    
    private Integer afterQty;

}
