package com.onlinebanking.common;

import com.onlinebanking.customer.Customer;
import com.onlinebanking.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

@Component
public class AuthCodeInterceptor extends HandlerInterceptorAdapter {
	private CustomerRepository customerRepository;

	@Autowired
	public AuthCodeInterceptor(CustomerRepository customerRepository){
		this.customerRepository = customerRepository;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
		if(request.getRequestURI().equals("/customer/login")
				|| request.getRequestURI().equals("/admin/login")
				|| request.getRequestURI().equals("/token/check")
				|| request.getRequestURI().equals("/requests/new_customer")){
			return super.preHandle(request, response, handler);
		}

		String token = request.getHeader("Token");
		if(token != null){
			Customer customer = customerRepository.findByToken(token);
			if(customer != null) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MINUTE, -30);
				Date d = cal.getTime();
				if(d.compareTo(customer.getTokenCreated()) < 0) {
					return super.preHandle(request, response, handler);
				}
			}
		}
		response.setContentType("application/json");
		response.getWriter().append("{\"code\": 9999, \"message\":\"Failed\", \"data\":{\"message\":\"Token is incorrect.\"} }");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		super.postHandle(request, response, handler, modelAndView);
	}
}
