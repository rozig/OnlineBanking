package com.onlinebanking.customer;

import com.onlinebanking.common.Response;
import org.springframework.web.bind.annotation.*;

import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import java.util.Date;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {
    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

//    @PostMapping("/sign-up")
//    public void signUp(@RequestBody Customer customer) {
//        customer.setPassword(customer.getPassword());
//        customerRepository.save(customer);
//    }

    @PostMapping(value="/login", produces="application/json")
    public @ResponseBody
	Response login(@RequestBody String json) {
        JsonParser parser = new JsonParser();
		Map<String, Object> data = new HashMap<>();
        JsonObject o = parser.parse(json).getAsJsonObject();
        String email = o.get("email").getAsString();
        String password = o.get("password").getAsString();
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null) {
        	data.put("message", "EmailId or Password is incorrect");
            return new Response(611, "Failed", data);
        }
        if(customer.getPassword().equals(password)) {
            String token = null;
            try {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String tokenText = customer.getId() + timestamp.toString();
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(tokenText.getBytes(StandardCharsets.UTF_8));
                token = Base64.getEncoder().encodeToString(hash);
            } catch(NoSuchAlgorithmException nsae) {
                System.err.println(nsae.getMessage());
            }
            customer.setToken(token);
            customer.setTokenCreated(new Date());
            customerRepository.save(customer);
            data.put("token", token);
            return new Response(612, "Successful", data);
        } else {
        	data.put("message", "EmailId or Password is incorrect.");
            return new Response(613, "Failed", data);
        }
    }

	@PostMapping("/details")
	public @ResponseBody
	Customer customerDetails(@RequestBody String jsonInput){
//		Reading input datas from input json
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(jsonInput).getAsJsonObject();
		Long customerId = jsonObject.get("customerId").getAsLong();
		return customerRepository.findById(customerId);
	}
}
