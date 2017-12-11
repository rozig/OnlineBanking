package com.onlinebanking.common;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class AuthCodeInterceptor extends HandlerInterceptorAdapter {
//	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private CustomerRepository customerRepository;

//	@Autowired
//	public AuthCodeInterceptor(CustomerRepository customerRepository){
//		this.customerRepository = customerRepository;
//	}

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
//		if(request.getRequestURI().equals("/customer/login")
//				|| request.getRequestURI().equals("/admin/login")
//				|| request.getRequestURI().equals("/token/check")
//				|| request.getRequestURI().equals("/statement")
//				|| request.getRequestURI().equals("/requests/new_customer")){

//			String inputData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

//			CustomLogger.getInstance().info( "<<" + inputData);
			return super.preHandle(request, response, handler);
////		}
//
//		String token = request.getHeader("Authorization");
//		if(token != null){
//			Customer customer = customerRepository.findByToken(token);
//			if(customer != null) {
//				Calendar cal = Calendar.getInstance();
//				cal.add(Calendar.MINUTE, -30);
//				Date d = cal.getTime();
//				if(d.compareTo(customer.getTokenCreated()) < 0) {
//					return super.preHandle(request, response, handler);
//				}
//			}
//		}
//		response.setContentType("application/json");
//		response.getWriter().append("{\"code\": 9999, \"message\":\"Failed\", \"data\":{\"message\":\"Token is incorrect.\"} }");
//		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
}
