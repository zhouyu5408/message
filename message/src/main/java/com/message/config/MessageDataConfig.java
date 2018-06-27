package com.message.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.message.service.impl.MsgServiceScan;

@Configuration
@EnableTransactionManagement
@MapperScan({ "com.message.dao"})
@Import({MsgServiceScan.class})
@DependsOn("messageProperties")
public class MessageDataConfig {
	@SuppressWarnings("unused")
	@Autowired
	private Environment environment;
	@Autowired
	private MessageProperties messageProperties;

	@Bean
	public BasicDataSource msgDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(messageProperties.getUrl());
		dataSource.setDriverClassName(messageProperties.getDriverClass());
		dataSource.setUsername(messageProperties.getUsername());
		dataSource.setPassword(messageProperties.getPassword());

		dataSource.setInitialSize(messageProperties.getInitialSize());
		dataSource.setMaxActive(messageProperties.getMaxActive());
		dataSource.setMaxIdle(messageProperties.getMaxIdle());
		dataSource.setMinIdle(messageProperties.getMinIdle());
		dataSource.setMaxWait(messageProperties.getMaxWait());

		// dataSource.setUrl(environment.getProperty("message.url"));
		// dataSource.setDriverClassName(environment.getProperty("message.driverClass"));
		// dataSource.setUsername(environment.getProperty("message.username"));
		// dataSource.setPassword(environment.getProperty("message.password"));
		// dataSource.setInitialSize(environment.getProperty("message.initialSize",
		// Integer.class));
		// dataSource.setMaxActive(environment.getProperty("message.maxActive",
		// Integer.class));
		// dataSource.setMaxIdle(environment.getProperty("message.maxIdle",
		// Integer.class));
		// dataSource.setMinIdle(environment.getProperty("message.minIdle",
		// Integer.class));
		// dataSource.setMaxWait(environment.getProperty("message.maxWait",
		// Integer.class));
		return dataSource;
	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(msgDataSource());
		sessionFactoryBean.setConfigLocation(new ClassPathResource("/mybatis_cfg.xml"));
		sessionFactoryBean
				.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/message/*.xml"));
		return sessionFactoryBean;
	}

	/**
	 * 事务 管理
	 * 
	 * @param driverManagerDataSource
	 * @return
	 */
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(DataSource driverManagerDataSource) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(driverManagerDataSource);
		return transactionManager;
	}

	// @Bean
	// public IMsgService getService() {
	// return new MsgServiceImpl();
	// }
}
