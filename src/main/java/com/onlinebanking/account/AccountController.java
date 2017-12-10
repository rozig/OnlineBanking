package com.onlinebanking.account;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

	@Autowired
	AccountRepository accountRepository;

	@RequestMapping("/details")
	public @ResponseBody
	Account accountDetails(@RequestBody String jsonInput){
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		Long accountId = jsonObject.get("accountId").getAsLong();
		return accountRepository.findByAccountId(accountId);
	}
}
