package com.onlinebanking.accountbook;

import com.onlinebanking.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long>{
	public AccountBook findByCustomer(Customer customer);
}
