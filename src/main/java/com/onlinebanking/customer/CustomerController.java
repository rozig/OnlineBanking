package com.onlinebanking.customer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Customer customer) {
        customer.setPassword(customer.getPassword());
        customerRepository.save(customer);
    }

    @PostMapping(value="/login", produces="application/json")
    public String login(@RequestBody String json) {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(json).getAsJsonObject();
        String email = o.get("email").getAsString();
        String password = o.get("password").getAsString();
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null) {
            return "{\"code\": 2000, \"msg\":\"Username or password is incorrect.\"}";
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
            return "{\"code\": 1000, \"msg\": \"Login successful\", \"data\": {\"token\": \"" + token + "\"}}";
        } else {
            return "{\"code\": 2000, \"msg\":\"Username or password is incorrect.\"}";
        }
    }
}
