package com.onlinebanking.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthCodeInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {

		// set few parameters to handle ajax request from different host
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST");
		response.addHeader("Access-Control-Max-Age", "1000");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		response.addHeader("Cache-Control", "private");

		String reqUri = request.getRequestURI();
		String serviceName = reqUri.substring(reqUri.lastIndexOf("/") + 1,
				reqUri.length());
		if (serviceName.equals("SOMETHING")) {

		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}
}
