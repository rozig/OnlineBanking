package com.onlinebanking.transaction;

import com.onlinebanking.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class TransactionController {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@PostMapping(value = "/transaction")
	public void doTransaction(@RequestBody Transaction tran){

		System.out.println("in doTransaction function");
//		Transaction tran = new Transaction();
//		tran.setCreditAccount(accountRepository.findOne(crAcct));
//		tran.setDebitAccount(accountRepository.findOne(drAcct));
//		tran.setAmount(amount);
//		tran.setChannelId(channelId);
		tran.setTransactionDate(new Date());

		tran = transactionRepository.save(tran);

		if(tran.getTransactionId() != null){
			System.out.println("Transaction success : " + tran.getTransactionId());
		} else {
			System.out.println("Transaction failed");
		}
	}
}
