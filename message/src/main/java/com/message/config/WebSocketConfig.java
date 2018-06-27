package com.message.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.message.interceptors.HandshakeInterceptor;
import com.message.invoker.impl.ServiceScan;
import com.message.popup.PopupHandler;
import com.message.spring.SpringManager;

/**
 * 配置类文件，在web.xml文件中需要加载该文件
 * 
 * @Date: 2017年12月27日 上午10:24:04 <br/>
 *
 * @author zhouyu
 * @version 1.0 Copyright (YLINK) , All Rights Reserved.
 * @since JDK 1.7
 */
@Configuration
@EnableWebSocket
@Import({ MessageDataConfig.class, ServiceScan.class })
public class WebSocketConfig implements WebSocketConfigurer {

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(popupHandler(), "/websocket").addInterceptors(new HandshakeInterceptor());
		registry.addHandler(popupHandler(), "/sockjs").addInterceptors(new HandshakeInterceptor()).withSockJS();
	}

	@Bean
	public PopupHandler popupHandler() {
		return new PopupHandler();
	}

	@Bean
	public SpringManager springManager() {
		return new SpringManager();
	}

//	@Bean
//	public MessageProperties messageProperties() {
//		MessageProperties messageProperties = new MessageProperties();
//		messageProperties.setUrl("jdbc:mysql://192.168.11.197:3306/ylink?useUnicode=true&amp;characterEncoding=UTF-8");
//		messageProperties.setDriverClass("com.mysql.jdbc.Driver");
//		messageProperties.setUsername("root");
//		messageProperties.setPassword("root");
//		messageProperties.setInitialSize(3);
//		messageProperties.setMaxActive(5);
//		messageProperties.setMaxIdle(2);
//		messageProperties.setMinIdle(2);
//		messageProperties.setMaxWait(60000);
//		return messageProperties;
//	}

}
