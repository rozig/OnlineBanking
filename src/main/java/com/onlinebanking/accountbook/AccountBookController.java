package com.onlinebanking.accountbook;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.onlinebanking.common.Response;
import com.onlinebanking.customer.Customer;
import com.onlinebanking.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account_book")
@CrossOrigin
public class AccountBookController {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AccountBookRepository accountBookRepository;

	@PostMapping("/fetch_by_customer")
	public @ResponseBody
	AccountBook fetch (@RequestBody String jsonInput){
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		Long customerId = jsonObject.get("customerId").getAsLong();
		Customer customer = customerRepository.findById(customerId);
		if(customer != null){
			return accountBookRepository.findByCustomer(customer);
		}

		return null;
	}

	@PostMapping("/create_account_book")
	public @ResponseBody
	Response createAccountBook(HttpServletRequest req){
		String token = req.getHeader("Token");
		Customer customer = customerRepository.findByToken(token);
		Map<String, Object> data = new HashMap<>();
		if(customer != null){
			AccountBook newAccountBook = new AccountBook();
			newAccountBook.setCustomer(customer);
			newAccountBook = accountBookRepository.save(newAccountBook);
			if(newAccountBook.getId() != null){
				data.put("message", "");
				return new Response(411, "Successful", data);
			}
		}
		data.put("message", "Try again later.");
		return new Response(412, "Failed", data);
	}
}
