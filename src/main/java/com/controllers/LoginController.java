package com.controllers;

import com.database.repositories.UserRepository;
import com.models.CallResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping (value = "/login", method= RequestMethod.POST)
    public CallResponse login(@RequestBody String userName, String password){

        return null;
    }

}
