package com.onlinebanking.request;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.onlinebanking.account.Account;
import com.onlinebanking.account.AccountRepository;
import com.onlinebanking.account.SavingAccount;
import com.onlinebanking.account.SavingAccountRepository;
import com.onlinebanking.accountbook.AccountBook;
import com.onlinebanking.accountbook.AccountBookRepository;
import com.onlinebanking.admin.Admin;
import com.onlinebanking.admin.AdminRepository;
import com.onlinebanking.common.CustomLogger;
import com.onlinebanking.common.Response;
import com.onlinebanking.common.TokenGenerator;
import com.onlinebanking.customer.Customer;
import com.onlinebanking.customer.CustomerRepository;
import com.onlinebanking.notification.EmailServiceImpl;
import com.onlinebanking.rulefactory.Rule;
import com.onlinebanking.rulefactory.RuleFactory;
import com.onlinebanking.rulefactory.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/requests")
@CrossOrigin
public class RequestController {
	@Autowired
	private RequestRepository requestRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountBookRepository accountBookRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private SavingAccountRepository savingAccountRepository;

	@Autowired
	private EmailServiceImpl emailService;

	@Autowired
	private RuleRepository ruleRepository;

	public RequestController(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}

	@PostMapping(value = "/account")
	public @ResponseBody
	Response addRequest(@RequestBody String jsonInput) {
		CustomLogger.getInstance().info(jsonInput);
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		Map<String, Object> data = new HashMap<>();
		try {
			JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
			Long customerId = jsonObject.get("customerId").getAsLong();
			RequestType requestType = RequestType.valueOf(jsonObject.get("requestType").getAsString());
			Customer customer = customerRepository.findById(customerId);
			if (customer == null) {
				data.put("message", "Invalid customer Id");
				return new Response(211, "Failed", data);
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
					data.put("message", "Account creation failed");
					return new Response(212, "Failed", data);
				}

				if (requestType.equals(RequestType.OpenSavingAccount)) {
					SavingAccount sa = new SavingAccount();
					sa.setInterestRate(15);
					mDate.add(Calendar.MONTH, 6);
					sa.setMaturityDate(mDate.getTime());
					sa.setAccount(account);
					sa = savingAccountRepository.save(sa);

					if (sa.getAccountId() == null) {
						data.put("message", "Account creation failed");
						return new Response(213, "Failed", data);
					}

					account.setSavingAccount(sa);
					account = accountRepository.save(account);
				}
			} else if (requestType.equals(RequestType.CloseAccount)) {
				if (!jsonObject.has("accountId")) {
					data.put("message", "Invalid request");
					return new Response(217, "Failed", data);
				}
				Long accountId = jsonObject.get("accountId").getAsLong();
				account = accountRepository.findByAccountId(accountId);
				if (account == null) {
					data.put("message", "Invalid account Id");
					return new Response(218, "Failed", data);
				}
			} else {
				data.put("message", "Invalid request");
				return new Response(214, "Failed", data);
			}

			Request request = new Request();
			request.setType(requestType);
			request.setStatus(RequestStatus.Pending);
			request.setAccount(account);
			request.setCustomer(customer);
			request.setCreatedDate(new Date());
			request = requestRepository.save(request);

			if (request.getId() != null) {
				data.put("message", "");
				return new Response(215, "Successful", data);
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		data.put("message", "Request creation failed.");
		return new Response(216, "Failed", data);
	}

	@PostMapping(value = "/new_customer")
	public @ResponseBody
	Response addNewCustomerRequest(@RequestBody String jsonInput){
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		Map<String, Object> data = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
			String firstName = jsonObject.get("firstName").getAsString();
			String lastName = jsonObject.get("lastName").getAsString();
			String emailId = jsonObject.get("emailId").getAsString();
			String mobileNum = jsonObject.get("mobileNum").getAsString();
			Date dateOfBirth = sdf.parse(jsonObject.get("dateOfBirth").getAsString());
			String ssn = jsonObject.get("ssn").getAsString();
			Double monthlyIncome = jsonObject.get("monthlyIncome").getAsDouble();
			Double creditScore = jsonObject.get("creditScore").getAsDouble();

			Customer newCustomer = new Customer();
			newCustomer.setFirstname(firstName);
			newCustomer.setLastname(lastName);
			newCustomer.setEmail(emailId);
			newCustomer.setPhoneNumber(mobileNum);
			newCustomer.setDateOfBirth(dateOfBirth);
			newCustomer.setSsn(ssn);
			newCustomer.setPassword(TokenGenerator.getPassword(10));
			newCustomer.setIsActivated("N");
			newCustomer.setCreditScore(creditScore);
			newCustomer.setMonthlyIncome(monthlyIncome);

			newCustomer = customerRepository.save(newCustomer);

			Rule rule = RuleFactory.getRule(newCustomer);

			ruleRepository.save(rule);
			newCustomer.setRule(rule);
			newCustomer = customerRepository.save(newCustomer);

			if(newCustomer.getId() == null){
				data.put("message", "Try again later");
				return new Response(221, "Failed", data);
			}

			Account newAccount = new Account();
			newAccount.setBalance(0);
			newAccount.setIsActivated("N");
			newAccount.setIsDefault("Y");
			newAccount.setAccountOpenDate(new Date());
			newAccount.setCustomer(newCustomer);
			newAccount.setAccountName(firstName);

			newAccount = accountRepository.save(newAccount);

			if(newAccount.getAccountId() == null){
				data.put("message", "Try again later.");
				return new Response(222, "Failed", data);
			}

			newCustomer.getAccountSet().add(newAccount);
			customerRepository.save(newCustomer);

			Request request = new Request();
			request.setCreatedDate(new Date());
			request.setCustomer(newCustomer);
			request.setAccount(newAccount);
			request.setType(RequestType.RegisterCustomer);
			request.setStatus(RequestStatus.Pending);
			request = requestRepository.save(request);
			if(request.getId() == null){
				data.put("message", "Try again later");
				return new Response(223, "Failed", data);
			}

			data.put("message", "");
			return new Response(224, "Successful", data);

		} catch (Exception e){
			e.printStackTrace();
			System.out.println("225 " + e);
		}

		data.put("message", "Try again later.");
		return new Response(226, "Failed", data);
	}

	@PostMapping(value = "/verify")
	public @ResponseBody Response verifyRequest(@RequestBody String jsonInput, HttpServletRequest req){
		Map<String, Object> data = new HashMap<>();
//		String token = req.getHeader("Token");
//		Admin admin = adminRepository.findByToken(token);
//		if(admin == null){
//			data.put("message", "You dont have a permission");
//			return new Response(241, "Failed", data);
//		}

//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		try {
			JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
			Long requestId = jsonObject.get("requestId").getAsLong();
			Request request = requestRepository.findById(requestId);
			if (request == null) {
				data.put("message", "Invalid request Id.");
				return new Response(242, "Failed", data);
			}

			Customer customer = request.getCustomer();
			String email = customer.getEmail();

			if (request.getType().equals(RequestType.OpenCheckingAccount)
					|| request.getType().equals(RequestType.OpenSavingAccount)) {
				Account account = request.getAccount();
				account.setIsActivated("Y");
				accountRepository.save(account);
				request.setStatus(RequestStatus.Completed);
				requestRepository.save(request);
				emailService.sendSimpleMessage(email, "Opening account request approved",
						"Now, Your new account is ready for needs. accountId is " + account.getAccountId() + ".");
				data.put("message", "");
				return new Response(243, "Successful", data);
			} else if (request.getType().equals(RequestType.CloseAccount)) {
				Account account = request.getAccount();
				account.setIsActivated("N");
				accountRepository.save(account);
				request.setStatus(RequestStatus.Completed);
				requestRepository.save(request);
				emailService.sendSimpleMessage(email, "Closing account request approved",
						"Now, Your account is closed. accountId is " + account.getAccountId() + ".");
				data.put("message", "");
				return new Response(244, "Successful", data);
			} else if (request.getType().equals(RequestType.RegisterCustomer)) {
				AccountBook newAccountBook = new AccountBook();
				newAccountBook.setCustomer(customer);
				accountBookRepository.save(newAccountBook);
				customer.setIsActivated("Y");
				customerRepository.save(customer);
				Account account = request.getAccount();
				account.setIsActivated("Y");
				accountRepository.save(account);
				request.setStatus(RequestStatus.Completed);
				requestRepository.save(request);
				emailService.sendSimpleMessage(email, "Welcome to Online Banking",
						"Finally, You are registered our online banking system. " +
								"Please sign in with emailId and following password " + customer.getPassword() + " .");
				data.put("message", "");
				return new Response(245, "Successful", data);
			}
		} catch(Exception e){
			e.printStackTrace();
		}

		data.put("message", "Try again later");
		return new Response(246, "Failed", data);
	}

	@PostMapping("/deleteRequest")
	public @ResponseBody Response deleteRequest(@RequestBody String jsonInput, HttpServletRequest req) {
		Map<String, Object> data = new HashMap<>();
//		String token = req.getHeader("Token");
//		Customer customer = customerRepository.findByToken(token);
//		if(customer == null){
//			data.put("message", "You dont have a permission");
//			return new Response(251, "Failed", data);
//		}

//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		try {
			Long requestId = jsonObject.get("requestId").getAsLong();
			Request request = requestRepository.findById(requestId);
			if (request == null) {
				data.put("message", "Invalid request Id.");
				return new Response(251, "Failed", data);
			}

			if (request.getStatus().equals(RequestStatus.Pending)) {
				if (request.getType().equals(RequestType.OpenCheckingAccount)
						|| request.getType().equals(RequestType.OpenSavingAccount)) {
					Account account = request.getAccount();
					account.setIsActivated("N");
					accountRepository.save(account);
					requestRepository.delete(request);
					data.put("message", "");
					return new Response(252, "Successful", data);
				} else if (request.getType().equals(RequestType.CloseAccount)) {
					requestRepository.delete(request);
					data.put("message", "");
					return new Response(253, "Successful", data);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		data.put("message", "Try again later");
		return new Response(254, "Failed", data);
	}

	@PostMapping(value = "/list")
	public @ResponseBody
	List<Request> requestList(@RequestBody String jsonInput){
		try {
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
			Long customerId = jsonObject.get("customerId").getAsLong();
			if (customerId != null) {
				Customer customer = customerRepository.findById(customerId);
				if (customer != null) {
					return requestRepository.findByCustomer(customer);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping(value = "/all_list")
	public @ResponseBody
	List<Request> allRequestList(){
		return requestRepository.findAll();
	}
}
