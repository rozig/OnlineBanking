package com.onlinebanking.user;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.onlinebanking.admin.Admin;
import com.onlinebanking.admin.AdminRepository;
import com.onlinebanking.common.Response;
import com.onlinebanking.customer.Customer;
import com.onlinebanking.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	CustomerRepository customerRepository;

	@RequestMapping("/check")
	public @ResponseBody
	Response checkToken(@RequestBody String jsonInput){
		Map<String, Object> data = new HashMap<>();
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		String token = jsonObject.get("token").getAsString();
		String type = jsonObject.get("type").getAsString();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -30);

		if(type.equalsIgnoreCase("admin")) {
			Admin admin = adminRepository.findByToken(token);
			if(admin != null){
				if(cal.getTime().compareTo(admin.getTokenCreated()) < 0){
					data.put("message", "Success");
					data.put("token", token);
					data.put("isActive", true);
					data.put("userId", admin.getId());
					return new Response(811, "Success", data);
				} else {
					data = new HashMap<>();
					data.put("message", "Token expired");
					data.put("isActive", false);
					return new Response(812, "Failed", data);
				}
			}
		} else if(type.equalsIgnoreCase("customer")) {
			Customer customer = customerRepository.findByToken(token);
			if(customer != null){
				if(cal.getTime().compareTo(customer.getTokenCreated()) < 0){
					data.put("message", "Success");
					data.put("token", token);
					data.put("isActive", true);
					data.put("userId", customer.getId());
					return new Response(813, "Success", data);
				} else {
					data = new HashMap<>();
					data.put("message", "Token expired");
					data.put("isActive", false);
					return new Response(814, "Failed", data);
				}
			}
		} else {
			data.put("message", "Invalid request");
			return new Response(815, "Failed", data);
		}

		data.put("message", "Invalid token");
		return new Response(816, "Failed", data);
	}
}
