package com.onlinebanking.account;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

@PostMapping
	public String doTransaction(String AccountId, String CreditId, String ChannelId, double amount){
		switch (ChannelId){
			case "ByAccountId":
				break;
			case "ByMobileNum":
				break;
			case "ByEmailId":
				break;
		}



		return "Success";
	}
}
