package com.onlinebanking.accountbook;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.onlinebanking.account.AccountRepository;
import com.onlinebanking.common.CustomLogger;
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

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	ItemRepository itemRepository;

	@PostMapping("/fetch_by_customer")
	public @ResponseBody
	AccountBook fetch (@RequestBody String jsonInput){
		CustomLogger.getInstance().info(jsonInput);
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

	@PostMapping("/add_to_account_book")
	public @ResponseBody
	Response addToAccountBook(@RequestBody String jsonInput){
		Map<String, Object> data = new HashMap<>();
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		Long customerId = jsonObject.get("customerId").getAsLong();
		Long accountId = jsonObject.get("accountId").getAsLong();
		String itemName = jsonObject.get("itemName").getAsString();

		Customer customer = customerRepository.findById(customerId);
		if(customer != null){
			AccountBook ab = accountBookRepository.findByCustomer(customer);
			Item item = new Item();
			item.setAccount(accountRepository.findOne(accountId));
			item.setAccountBook(ab);
			item.setItemName(itemName);
			itemRepository.save(item);
			data.put("message", "");
			return new Response(411, "Successful", data);
		}
		if(customer != null){
			AccountBook newAccountBook = new AccountBook();
			newAccountBook.setCustomer(customer);
			newAccountBook = accountBookRepository.save(newAccountBook);
			if(newAccountBook.getId() != null){
				data.put("message", "");
				return new Response(412, "Successful", data);
			}
		}
		data.put("message", "Try again later.");
		return new Response(413, "Failed", data);
	}
}
