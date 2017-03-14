package com.esy.stack.util;



public class Constants {
	
	public static final String BASE_CHARSET = "utf-8";
	
	/**新浪股票所有页数*/
	public static final int PAGE_SIZE = 42;
	/**新浪股票页面共有42页*/
	public static final String SINA_ALL_CODE_PATH = "http://vip.stock.finance.sina.com.cn/q/go.php/vIR_CustomSearch/index.phtml?p=";
	/**新浪股票详细信息地址*/
	public static final String SINA_CODE_DETAIL = "http://hq.sinajs.cn/?list=";
	
	
	/** httpclient相关参数设置，参考开涛博客：http://jinnianshilongnian.iteye.com/blog/2089792 */
	/**设置连接超时时间*/
	public static final Integer CONNECTION_TIMEOUT = 2 * 1000; //设置请求超时2秒钟 
	public static final Integer SO_TIMEOUT = 2 * 1000; //设置等待数据超时时间2秒钟 
	
	/**
	 * 文本相似度阀值（小于 0.8 视为不一样）
	 */
	public static final double HTML_CHANGE_RATIO = 0.8;
	
	public static final String HANDLER_PROP_PATH = "com/esy/stack/download/prop.properties";
	/**
	 * href
	 */
	public static final String ATTR_HREF = "href";
	
}
