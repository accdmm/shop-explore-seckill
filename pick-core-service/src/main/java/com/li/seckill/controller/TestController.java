package com.li.seckill.controller;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.Resource;
import com.li.seckill.dto.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 测试
 * @author: 李梦豪
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private MeterRegistry meterRegistry;
    
    @RequestMapping("/meter")
    public Result<String> queryShopById() {
        Counter counter = meterRegistry.counter("test_query_shop", "method", "queryShopById");
        counter.increment();
        return Result.ok("指标上报成功，当前计数: " + counter.count());
    }
}
