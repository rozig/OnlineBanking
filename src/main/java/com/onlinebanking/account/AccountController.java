package com.onlinebanking.account;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.onlinebanking.common.CustomLogger;
import com.onlinebanking.customer.Customer;
import com.onlinebanking.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@PostMapping("/details")
	public @ResponseBody
	Account accountDetails(@RequestBody String jsonInput){
		CustomLogger.getInstance().info(jsonInput);
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		Long accountId = jsonObject.get("accountId").getAsLong();
		return accountRepository.findByAccountId(accountId);
	}

	@PostMapping("/list")
	public @ResponseBody
	Set<Account> accountList(@RequestBody String jsonInput){
		CustomLogger.getInstance().info(jsonInput);
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		Long customerId = jsonObject.get("customerId").getAsLong();
		Customer customer = customerRepository.findById(customerId);
		return accountRepository.getAccountList(customer);
	}
}
