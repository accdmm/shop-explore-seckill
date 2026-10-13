package com.li.seckill.exception;

import lombok.Data;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 参数错误
 * @author: 李梦豪
 **/
@Data
public class ArgumentError {
	
	private String argumentName;
	
	private String message;
}
