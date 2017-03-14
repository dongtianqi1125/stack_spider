package com.esy.stack.util;

import lombok.Getter;

/**
 * 处理状态
 * <p>通用
 * @author guanjie
 *
 */
public enum StatusEnum {
	
	/**废弃*/
	ABANDOM(-2),
	/**失败*/
	FAILURE(-1),
	/**待处理*/
	WAIT(0),
	/**正在处理*/
	PROCESSING(1),
	/**处理成功*/
	SUCCESS(2);
	
	@Getter
	private int value;

	private StatusEnum(int value) {
		this.value = value;
	}
}
