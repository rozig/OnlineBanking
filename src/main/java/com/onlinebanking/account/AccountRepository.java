package com.onlinebanking.account;


import com.onlinebanking.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
	public Account findByAccountId(Long Id);

	@Query("select a.accountId, a.accountName from Account a " +
			"where a.customer = :customer ")
	public List<Account> getAccountList(@Param("customer") Customer customer);
}
