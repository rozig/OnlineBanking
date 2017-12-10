package com.onlinebanking.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {
	@Autowired
	AuthCodeInterceptor authCodeInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authCodeInterceptor);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
}
