/**
 * @(#)ParameterException.java 2011-12-20 Copyright 2011 it.kedacom.com, Inc.
 *                             All rights reserved.
 */

package com.li.seckill.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 参数异常
 * @author: 李梦豪
 **/

@EqualsAndHashCode(callSuper = true)
@Data
public class ArgumentException extends BaseException {
	
	private List<ArgumentError> argumentErrorList;
	
	public ArgumentException(List<ArgumentError> argumentErrorList) {
		this.argumentErrorList = argumentErrorList;
	}

	public ArgumentException(String message) {
		super(message);
	}
	

	public ArgumentException(Throwable cause) {
		super(cause);
	}

	public ArgumentException(String message, Throwable cause) {
		super(message, cause);
	}
}
