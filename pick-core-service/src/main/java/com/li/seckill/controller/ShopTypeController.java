package com.li.seckill.controller;


import jakarta.annotation.Resource;
import com.li.seckill.dto.Result;
import com.li.seckill.entity.ShopType;
import com.li.seckill.service.IShopTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 商铺类型api
 * @author: 李梦豪
 **/
@RestController
@RequestMapping("/shop-type")
public class ShopTypeController {
    @Resource
    private IShopTypeService typeService;

    @GetMapping("list")
    public Result queryTypeList() {
        List<ShopType> typeList = typeService
                .query().orderByAsc("sort").list();
        return Result.ok(typeList);
    }
}
