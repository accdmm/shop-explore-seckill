package com.li.seckill.toolkit;

import lombok.Data;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: workId和dataCenterId对象
 * @author: 李梦豪
 **/
@Data
public class WorkDataCenterId {

    private Long workId;
    
    private Long dataCenterId;
}
