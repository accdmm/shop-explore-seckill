package com.li.seckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 服务启动类
 * @author: 李梦豪
 **/
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.li.seckill.mapper")
@SpringBootApplication
public class ShopPickApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopPickApplication.class, args);
    }

}
