package com.controllers;

import com.models.User;
import com.repositories.SavingAccountRepository;
import com.repositories.UserRepository;
import com.models.CallResponse;
import com.models.SavingAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavingAccountRepository savingAccountRepository;

    @RequestMapping (value = "/login", method= RequestMethod.POST)
    public CallResponse login(@RequestBody User inputUser){
        System.out.println("Username : " + inputUser.getUserName());
        System.out.println("Password : " + inputUser.getPassword());
//        User userDummy = new User();
//        userDummy.setFirstName("Tamir");
//        userDummy.setLastName("Batmunkh");
//        userDummy.setUserName("Tamir");
//        userDummy.setPassword("password");
//        userDummy.setEmail("tamirsnarf@gmail.com");
//        userDummy.setMobileNum("99180558");
//        userDummy.setDateOfBirth(new Date());
//        userRepository.save(userDummy);

//        if(inputUser.getUserName().equals("") && inputUser.getPassword().equals("")){
//            User user = userRepository.findByUserName(inputUser.getUserName());
//            if(user.getPassword().equals(inputUser.getPassword())){
//                System.out.println("Login success");
//                System.out.println(user.getFirstName());
//                System.out.println(user.getLastName());
//            } else {
//                System.out.println("Login failed");
//            }
//        }

        SavingAccount sa = new SavingAccount();
        sa.setInterestRate(0.1);
        sa.setMaturityDate(new Date());
        sa.setAccountId("1505020711");
        sa.setAccountName("Tamir");
        sa.setBalance(250000);
        sa.setOpenedDate(new Date());
        sa.setCustomerId(123);
        savingAccountRepository.save(sa);


        return new CallResponse(1, new String[] {inputUser.getUserName()});
    }
}
