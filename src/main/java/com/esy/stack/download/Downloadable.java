package com.esy.stack.download;
/**
 * 
 * @author guanjie
 * 下载html的服务类需实现此接口，实现统一的方法调用
 *
 */
public interface Downloadable {
	
	/**
	 * 下载网页数据
	 */
	void download();
}
