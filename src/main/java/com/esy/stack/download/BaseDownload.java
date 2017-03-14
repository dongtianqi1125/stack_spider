package com.esy.stack.download;

import java.util.List;

/**
 * 稿件下载基础类
 * @author guanjie
 *
 */
public abstract class BaseDownload implements Downloadable{
	
	/**
	 * 通用处理，下载 网站栏目稿件
	 */
	@Override
	public void download() {
		
	}
	
	
	/**
	 * 获取可用的接口
	 * @return
	 */
	protected abstract List<String> getColumnUrls();

	
}
