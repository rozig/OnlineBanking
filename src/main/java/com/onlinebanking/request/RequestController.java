package com.onlinebanking.request;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.onlinebanking.account.Account;
import com.onlinebanking.account.AccountRepository;
import com.onlinebanking.account.SavingAccount;
import com.onlinebanking.account.SavingAccountRepository;
import com.onlinebanking.common.Response;
import com.onlinebanking.customer.Customer;
import com.onlinebanking.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {
	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private SavingAccountRepository savingAccountRepository;

	public RequestController(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}

	@PostMapping(value = "/open_account")
	public @ResponseBody
	Response addRequest(@RequestBody String jsonInput) {
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		Long customerId = jsonObject.get("customerId").getAsLong();
		RequestType requestType = RequestType.valueOf(jsonObject.get("requestType").getAsString());
		Customer customer = customerRepository.findById(customerId);
		if(customer.getId() == null){
			return new Response(211, "Failed", "Invalid customer Id.");
		}

		Account newAccount = new Account();
		if (requestType.equals(RequestType.OpenCheckingAccount) || requestType.equals(RequestType.OpenSavingAccount)) {
			Calendar mDate = Calendar.getInstance();
			newAccount.setAccountName(customer.getFirstname());
			newAccount.setCustomer(customer);
			newAccount.setAccountOpenDate(mDate.getTime());
			newAccount.setBalance(0.0);
			newAccount.setIsDefault("N");
			newAccount.setIsActivated("N");
			newAccount = accountRepository.save(newAccount);

			if (newAccount.getAccountId() == null) {
				return new Response(212, "Failed", "Account creation failed.");
			}

			if (requestType.equals(RequestType.OpenSavingAccount)) {
				SavingAccount sa = new SavingAccount();
				sa.setInterestRate(15);
				mDate.add(Calendar.MONTH, 6);
				sa.setMaturityDate(mDate.getTime());
				sa.setAccount(newAccount);
				sa = savingAccountRepository.save(sa);

				if (sa.getAccountId() == null) {
					return new Response(213, "Failed", "Account creation failed.");
				}

				newAccount.setSavingAccount(sa);
				newAccount = accountRepository.save(newAccount);
			}
		} else {
			return new Response(214, "Failed", "Invalid request time.");
		}

		Request request = new Request();
		request.setType(requestType);
		request.setStatus(RequestStatus.Pending);
		request.setAccount(newAccount);
		request.setCustomer(customer);
		request.setCreatedDate(new Date());
		request = requestRepository.save(request);

		if(request.getId() != null){
			return new Response(214, "Successful", "");
		}

		return new Response(215, "Failed", "Request creation failed.");
	}

	@GetMapping
	public List<Request> getRequests() {
		return requestRepository.findAll();
	}

	@DeleteMapping("/{id}")
	public void deleteRequest(@PathVariable long id) {
		requestRepository.delete(id);
	}
}
