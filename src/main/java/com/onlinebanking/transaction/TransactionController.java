package com.onlinebanking.transaction;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.onlinebanking.account.Account;
import com.onlinebanking.account.AccountRepository;
import com.onlinebanking.common.Response;
import com.onlinebanking.customer.Customer;
import com.onlinebanking.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class TransactionController {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@PostMapping(value = "/transaction")
	public @ResponseBody Response doTransaction(@RequestBody String json) {
		JsonParser parser = new JsonParser();
		JsonObject o = parser.parse(json).getAsJsonObject();
		String crId = o.get("crId").getAsString();
		Long drAcct = o.get("drAcct").getAsLong();
		double amount = o.get("amount").getAsDouble();
		String channelId = o.get("channelId").getAsString();
		Account drAccount = accountRepository.findOne(drAcct);

		Customer customer = new Customer();
		Account crAccount = new Account();

		switch (channelId){
			case "ByEmailId":
				customer = customerRepository.findByEmail(crId);
				if(customer == null){
					return new Response(111,"The customer is not found for the email.", "");
				}
				for(Account account : customer.getAccountSet()){
					if(account.getIsDefault().equals("Y")){
						crAccount = accountRepository.findOne(Long.parseLong(crId));
						break;
					}
				}

				if(crAccount == null){
					return new Response(112,"The customer has not chosen default account.", "");
				}

				break;
			case "ByMobileNum":
				customer = customerRepository.findByPhoneNumber(crId);
				if(customer == null){
					return new Response(113,"The customer is not found for the phonenumber.", "");
				}

				for(Account account : customer.getAccountSet()){
					if(account.getIsDefault().equals("Y")){
						crAccount = account;
						break;
					}
				}

				if(crAccount == null){
					return new Response(114,"The customer has not chosen default account.", "");
				}
				break;
			case "ByAccountId":
				crAccount = accountRepository.findOne(Long.parseLong(crId));
				if(crAccount == null){
					return new Response(115,"Account not found. Please double check the credit account", "");
				}
				break;
			default:
				return new Response(116,"Invalid channelId.", "");
		}
		System.out.println("in doTransaction function");
		Transaction tran = new Transaction();

		tran.setCreditAccount(crAccount);
		tran.setDebitAccount(drAccount);

		tran.setAmount(amount);
		tran.setChannelId(channelId);
		tran.setTransactionDate(new Date());

		tran = transactionRepository.save(tran);

		if(tran.getTransactionId() != null){
			crAccount.setBalance(crAccount.getBalance() + amount);
			accountRepository.save(crAccount);

			drAccount.setBalance(drAccount.getBalance() - amount);
			accountRepository.save(drAccount);

			return new Response(117,"The transaction succeed.", "");
		}

		return new Response(118,"The transaction failed. Please try again sometime later.", "");
	}
}
