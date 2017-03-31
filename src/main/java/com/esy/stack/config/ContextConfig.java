package com.esy.stack.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.esy.stack.util.Constants;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.*;

@Configuration
@ComponentScan(basePackages = {"com.esy.stack"})
public class ContextConfig{

	@Profile(value = "dev")
	@Bean(name = "jdbcConfig")
	Properties propertiesDev() throws IOException {
		Properties properties = new Properties();
		properties.load(new ClassPathResource("prop/jdbc_dev.properties").getInputStream());
		return properties;
	}
	@Profile(value = "prod")
	@Bean(name = "jdbcConfig")
	Properties propertiesProd() throws IOException {
		Properties properties = new Properties();
		properties.load(new ClassPathResource("prop/jdbc_prod.properties").getInputStream());
		return properties;
	}
	@Profile(value = "dev")
	@Bean(name = "serviceConfig")
	Properties configDev() throws IOException {
		Properties properties = new Properties();
		properties.load(new ClassPathResource("prop/config_dev.properties").getInputStream());
		return properties;
	}
	@Profile(value = "prod")
	@Bean(name = "serviceConfig")
	Properties configProd() throws IOException {
		Properties properties = new Properties();
		properties.load(new ClassPathResource("prop/config_prod.properties").getInputStream());
		return properties;
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource(@Qualifier("jdbcConfig") Properties env) throws PropertyVetoException {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setUrl(env.getProperty("jdbc.url"));
		druidDataSource.setUsername(env.getProperty("jdbc.user"));
		druidDataSource.setPassword(env.getProperty("jdbc.password"));
		druidDataSource.setMaxActive(500);
		return druidDataSource;
	}
	
	@Bean
	public ExecutorService executor() {
		return new ThreadPoolExecutor(10, 50, 100, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
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
		properties.load(new ClassPathResource("prop/config_dev.properties").getInputStream());
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
