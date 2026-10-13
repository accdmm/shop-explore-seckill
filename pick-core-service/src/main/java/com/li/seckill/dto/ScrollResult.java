package com.li.seckill.dto;

import lombok.Data;

import java.util.List;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 滚动-结果
 * @author: 李梦豪
 **/
@Data
public class ScrollResult {
    private List<?> list;
    private Long minTime;
    private Integer offset;
}
