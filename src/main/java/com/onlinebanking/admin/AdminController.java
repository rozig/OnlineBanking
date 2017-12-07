package com.onlinebanking.admin;

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
@RequestMapping("/admin")
public class AdminController {
    private AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Admin admin) {
        admin.setPassword(admin.getPassword());
        adminRepository.save(admin);
    }

    @PostMapping(value="/login", produces="application/json")
    public String login(@RequestBody String json, HttpServletRequest request, HttpSession session) {
        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(json).getAsJsonObject();
        String username = o.get("username").getAsString();
        String password = o.get("password").getAsString();
        Admin admin = adminRepository.findByUsername(username);
        if(admin == null) {
            return "{\"code\": 2000, \"msg\":\"Username or password is incorrect.\"}";
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
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String tokenCreated = dateFormat.format(new Date());
            admin.setToken(token);
            admin.setTokenCreated(tokenCreated);
            adminRepository.save(admin);
            return "{\"code\": 1000, \"msg\": \"Login successful\", \"data\": {\"token\": \"" + token + "\"}}";
        } else {
            return "{\"code\": 2000, \"msg\":\"Username or password is incorrect.\"}";
        }
    }
}