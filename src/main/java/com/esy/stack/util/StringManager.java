package com.esy.stack.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class StringManager {
	
	private static final Logger log = LoggerFactory.getLogger(StringManager.class);
	
	private StringManager(String fileName) {
		properties = new Properties();
		try {
			ClassPathResource resource = new ClassPathResource(fileName);
			properties.load(new InputStreamReader(resource.getInputStream(), "utf-8"));
		} catch (IOException e) {
			log.error("文件：" + fileName + " 读取异常 ! ", e);
		}
	}
	private Properties properties;
	
	private static Map<String, StringManager> cache = new HashMap<String, StringManager>();
	/**
	 * 以相对路径读取properties中的值
	 * @param fileName 文件名
	 * @param key
	 * @return
	 */
	public static StringManager getStringManageByFileName(String fileName){
		StringManager mgr = cache.get(fileName);
		if(mgr == null) {
			synchronized (cache) {
				mgr = cache.get(fileName);
				if(mgr == null) {
					mgr = new StringManager(fileName);
					cache.put(fileName, mgr);
				}
			}
		}
		return mgr;
	}
	
	
	public String getValue(String key) {
		return properties.getProperty(key);
	}
	
   public String getValue(String key, Object...args) {
        String value = getValue(key);
        return formatKeyString(value, args);
   }
   
   public int getIntValue(String key) {
	   String value = getValue(key);
	   if(value == null)
		   throw new NullPointerException();
	   return Integer.parseInt(value);
   }
   
   private static String formatKeyString(String key, Object...args) {
       try {
           return MessageFormat.format(key, args);
       } catch (IllegalArgumentException iae) {
           StringBuffer buf = new StringBuffer();
           buf.append(key);
           for (int i = 0; i < args.length; i++)
               buf.append(" arg[" + i + "]=" + args[i]);
           return buf.toString();
       }
   }
}
