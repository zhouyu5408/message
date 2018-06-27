package com.message.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.message.ctrl.CtrlScan;

@Configuration
@EnableWebMvc
@Import({ CtrlScan.class })
public class MessageMvc extends WebMvcConfigurerAdapter {
	/**
	 * 配置视图解析器
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

	/**
	 * 配置静态资源的处理
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
