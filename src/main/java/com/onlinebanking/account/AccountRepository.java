package com.onlinebanking.account;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	public Account findByAccountId(Long Id);
}
