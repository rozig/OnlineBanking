package com.onlinebanking.transaction;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.onlinebanking.account.Account;
import com.onlinebanking.account.AccountRepository;
import com.onlinebanking.common.CustomLogger;
import com.onlinebanking.common.Response;
import com.onlinebanking.customer.Customer;
import com.onlinebanking.customer.CustomerRepository;
import com.onlinebanking.notification.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
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
		Map<String, Object> data = new HashMap<>();
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		String crId = jsonObject.get("crId").getAsString();
		Long drAcct = jsonObject.get("drAcct").getAsLong();
		double amount = jsonObject.get("amount").getAsDouble();
		String channelId = jsonObject.get("channelId").getAsString();

//		Assigning debit account from repository
		Account drAccount = accountRepository.findOne(drAcct);

//		Validating whether credit account is activated or not
		if(!drAccount.getIsActivated().equals("Y")){
			data.put("message", "Debit account is closed or not activated.");
			return new Response(120, "Failed", data);
		}

//		Checking balance of account
		if(drAccount.getBalance() < amount){
			data.put("message", "Your account has not enough balance for the transaction.");
			return new Response(120, "Failed", data);
		}

		Customer customer = new Customer();
		Account crAccount = new Account();

//		checking channelId and credit account availability
		switch (channelId){
			case "ByEmailId":
				customer = customerRepository.findByEmail(crId);
				if(customer == null){
					data.put("message", "The customer is not found for the email. May be you can complete transaction by accountId.");
					return new Response(111,"Failed", data);
				}
				for(Account account : customer.getAccountSet()){
					if(account.getIsDefault().equals("Y")){
						crAccount = account;
						break;
					}
				}

				if(crAccount == null){
					data.put("message", "The customer has not chosen default account.");
					return new Response(112,"Failed", data);
				}

				break;
			case "ByMobileNum":
				customer = customerRepository.findByPhoneNumber(crId);
				if(customer == null){
					data.put("message", "The customer is not found for the phonenumber. May be you can complete transaction by accountId.");
					return new Response(113,"Failed", data);
				}

				for(Account account : customer.getAccountSet()){
					if(account.getIsDefault().equals("Y")){
						crAccount = account;
						break;
					}
				}

				if(crAccount == null){
					data.put("message", "The customer has not chosen default account.");
					return new Response(114,"Failed", data);
				}
				break;
			case "ByAccountId":
				crAccount = accountRepository.findOne(Long.parseLong(crId));
				if(crAccount == null){
					data.put("message", "Account not found. Please double check the credit account");
					return new Response(115,"Failed", data);
				}
				customer = crAccount.getCustomer();
				break;
			default:
				data.put("message", "Invalid channel Id.");
				return new Response(116,"Failed", data);
		}

//		Validating whether credit account is activated or not
		if(!crAccount.getIsActivated().equals("Y")){
			data.put("message", "Credit account is closed or not activated.");
			return new Response(119, "Failed", data);
		}

		if(drAccount.getCustomer().getRule().getMaxTranLimit() < amount){
			data.put("message", "Sorry your limit exceeded.");
			return new Response(119, "Failed", data);
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

			data.put("message","The transaction succeed.");
			return new Response(117,"Successful", data);
		}

		data.put("message", "The transaction failed. Please try again sometime later.");
		return new Response(118,"Failed", data);
	}

	@PostMapping(value = "/statement")
	public @ResponseBody List<Transaction> getTransactionList(@RequestBody String jsonInput){
		CustomLogger.getInstance().info(jsonInput);
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
