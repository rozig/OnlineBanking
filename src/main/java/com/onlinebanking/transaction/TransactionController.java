package com.onlinebanking.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.onlinebanking.account.Account;
import com.onlinebanking.account.AccountRepository;
import com.onlinebanking.common.Response;
import com.onlinebanking.customer.Customer;
import com.onlinebanking.customer.CustomerRepository;
import com.onlinebanking.notification.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
public class TransactionController {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	private EmailServiceImpl emailService;

	@PostMapping(value = "/transaction")
	public @ResponseBody Response doTransaction(@RequestBody String jsonInput) {
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		String crId = jsonObject.get("crId").getAsString();
		Long drAcct = jsonObject.get("drAcct").getAsLong();
		double amount = jsonObject.get("amount").getAsDouble();
		String channelId = jsonObject.get("channelId").getAsString();

//		Assigning debit account from repository
		Account drAccount = accountRepository.findOne(drAcct);

		Customer customer = new Customer();
		Account crAccount = new Account();

//		checking channelId and credit account availability
		switch (channelId){
			case "ByEmailId":
				customer = customerRepository.findByEmail(crId);
				if(customer == null){
					return new Response(111,"The customer is not found for the email. May be you can complete transaction by accountId.", "");
				}
				for(Account account : customer.getAccountSet()){
					if(account.getIsDefault().equals("Y")){
						crAccount = account;
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
					return new Response(113,"The customer is not found for the phonenumber. May be you can complete transaction by accountId.", "");
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

//		building the transaction object
		Transaction tran = new Transaction();
		tran.setCreditAccount(crAccount);
		tran.setDebitAccount(drAccount);
		tran.setAmount(amount);
		tran.setChannelId(channelId);
		tran.setTransactionDate(new Date());

		tran = transactionRepository.save(tran);

		if(tran.getTransactionId() != null){
//			if transaction is succeeded then update balance of accounts
			crAccount.setBalance(crAccount.getBalance() + amount);
			accountRepository.save(crAccount);

			drAccount.setBalance(drAccount.getBalance() - amount);
			accountRepository.save(drAccount);

			emailService.sendSimpleMessage(customer.getEmail(), "Transaction alert",
					drAccount.getCustomer().getFirstname() + " has sent " + amount + "$ to your " + crAccount.getAccountId() + " account.");

			return new Response(117,"The transaction succeed.", "");
		}

		return new Response(118,"The transaction failed. Please try again sometime later.", "");
	}

	@PostMapping(value = "/statement")
	public @ResponseBody List<Transaction> getTransactionList(@RequestBody String jsonInput){
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		Long accountId = jsonObject.get("accountId").getAsLong();
		Date fromDate, toDate;
		try {
			fromDate = simpleDateFormat.parse(jsonObject.get("fromDate").getAsString());
			toDate = simpleDateFormat.parse(jsonObject.get("toDate").getAsString());
			return transactionRepository.getStatement(accountId, fromDate, toDate);
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}
}
