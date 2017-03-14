package com.esy.stack.config;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.sql.DataSource;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import com.esy.stack.util.Constants;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@PropertySource(value = {"classpath:prop/jdbc.properties"})
@ComponentScan(basePackages = {"com.esy.stack"})
public class ContextConfig {
	@Autowired
	private Environment env;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(env.getProperty("jdbc.driverClass"));
		dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		dataSource.setUser(env.getProperty("jdbc.user"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		return dataSource;
	}
	
	@Bean
	public Executor executor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(10);
		executor.setQueueCapacity(25);
		return executor;
	}
	@Bean
	public TaskScheduler taskScheduler(){
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(10);
		return scheduler;
	}
	
	@Bean(name = "config")
	Properties properties() throws IOException {
		Properties properties = new Properties();
		properties.load(new ClassPathResource("prop/config.properties").getInputStream());
		return properties;
	}
	
	/**
	 * spring中rest请求客户端工具
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Constants.SO_TIMEOUT).setConnectTimeout(Constants.CONNECTION_TIMEOUT).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		return new RestTemplate(requestFactory);
	}
}
