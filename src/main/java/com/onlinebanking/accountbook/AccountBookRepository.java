package com.onlinebanking.accountbook;

import com.onlinebanking.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long>{
	public AccountBook findByCustomer(Customer customer);
}
