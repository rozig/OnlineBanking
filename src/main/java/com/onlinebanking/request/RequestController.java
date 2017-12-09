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

	@PostMapping(value = "/account")
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

		Account account = new Account();
		if (requestType.equals(RequestType.OpenCheckingAccount) || requestType.equals(RequestType.OpenSavingAccount)) {
			Calendar mDate = Calendar.getInstance();
			account.setAccountName(customer.getFirstname());
			account.setCustomer(customer);
			account.setAccountOpenDate(mDate.getTime());
			account.setBalance(0.0);
			account.setIsDefault("N");
			account.setIsActivated("N");
			account = accountRepository.save(account);

			if (account.getAccountId() == null) {
				return new Response(212, "Failed", "Account creation failed.");
			}

			if (requestType.equals(RequestType.OpenSavingAccount)) {
				SavingAccount sa = new SavingAccount();
				sa.setInterestRate(15);
				mDate.add(Calendar.MONTH, 6);
				sa.setMaturityDate(mDate.getTime());
				sa.setAccount(account);
				sa = savingAccountRepository.save(sa);

				if (sa.getAccountId() == null) {
					return new Response(213, "Failed", "Account creation failed.");
				}

				account.setSavingAccount(sa);
				account = accountRepository.save(account);
			}
		} else if(requestType.equals(RequestType.CloseAccount)) {
			if(!jsonObject.has("accountId")){
				return new Response(217, "Failed", "Invalid request");
			}
			Long accountId = jsonObject.get("accountId").getAsLong();
			account = accountRepository.findByAccountId(accountId);
			if(account.getAccountId() == null){
				return new Response(217, "Failed", "Invalid accountId");
			}
		} else {
			return new Response(214, "Failed", "Invalid request.");
		}

		Request request = new Request();
		request.setType(requestType);
		request.setStatus(RequestStatus.Pending);
		request.setAccount(account);
		request.setCustomer(customer);
		request.setCreatedDate(new Date());
		request = requestRepository.save(request);

		if(request.getId() != null){
			return new Response(215, "Successful", "");
		}

		return new Response(216, "Failed", "Request creation failed.");
	}

	@PostMapping(value = "/new_customer")
	public @ResponseBody
	Response addNewCustomerRequest(@RequestBody String jsonInput){
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		try {
			String firstName = jsonObject.get("firstName").getAsString();
			String lastName = jsonObject.get("lastName").getAsString();
			String emailId = jsonObject.get("emailId").getAsString();
			String mobileNum = jsonObject.get("mobileNum").getAsString();
			Date dateOfBirth = sdf.parse(jsonObject.get("dateOfBirth").getAsString());

			Customer newCustomer = new Customer();
			newCustomer.setFirstname(firstName);
			newCustomer.setLastname(lastName);
			newCustomer.setEmail(emailId);
			newCustomer.setPhoneNumber(mobileNum);
			newCustomer.setIsActivated("N");

		} catch (Exception e){
			System.out.println("221 " + e.getMessage());
			return new Response(222, "Failed", "Invalid request");
		}

		return null;
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
