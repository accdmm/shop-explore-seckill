package com.li.seckill.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import com.li.seckill.enums.BaseCode;

/**
 * @program: shop-pick 高并发秒杀平台
 * @description: 业务异常
 * @author: 李梦豪
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class PickFrameException extends BaseException {
	
	private Integer code;
	
	private String message;

	public PickFrameException() {
		super();
	}

	public PickFrameException(String message) {
		super(message);
	}
	
	public PickFrameException(Integer code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	
	public PickFrameException(BaseCode baseCode) {
		super(baseCode.getMsg());
		this.code = baseCode.getCode();
		this.message = baseCode.getMsg();
	}

	public PickFrameException(Throwable cause) {
		super(cause);
	}

	public PickFrameException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}
}
