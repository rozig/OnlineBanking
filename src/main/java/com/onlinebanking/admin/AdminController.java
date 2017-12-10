package com.onlinebanking.admin;

import com.onlinebanking.common.Response;
import org.springframework.web.bind.annotation.*;

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
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

//    @PostMapping("/sign-up")
//    public void signUp(@RequestBody Admin admin) {
//        admin.setPassword(admin.getPassword());
//        adminRepository.save(admin);
//    }

    @PostMapping(value="/login", produces="application/json")
    public @ResponseBody
	Response login(@RequestBody String json, HttpServletRequest request, HttpSession session) {
    	JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(json).getAsJsonObject();
        String email = o.get("email").getAsString();
        String password = o.get("password").getAsString();
        Admin admin = adminRepository.findByEmail(email);
		Map<String, Object> data = new HashMap<>();
        if(admin == null) {
			data.put("message", "Email or Password is incorrect");
        	return new Response(511, "Failed", data);
        }

        if(admin.getPassword().equals(password)) {
            String token = null;
            try {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String tokenText = admin.getId() + timestamp.toString();
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(tokenText.getBytes(StandardCharsets.UTF_8));
                token = Base64.getEncoder().encodeToString(hash);
            } catch(NoSuchAlgorithmException nsae) {
                System.err.println(nsae.getMessage());
            }
            admin.setToken(token);
            admin.setTokenCreated(new Date());
            adminRepository.save(admin);
			data.put("token", token);

            return new Response(512, "Successful", data);
        } else {
        	data.put("message", "Email or password is incorrect.");
			return new Response(512, "Successful", data);
        }
    }
}
