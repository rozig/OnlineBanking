package com.onlinebanking.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	@Query("select a from Transaction a " +
			"where (a.creditAccount.accountId = :accountId or a.debitAccount.accountId = :accountId)" +
			"and a.transactionDate >= :fromDate and a.transactionDate <= :toDate")
	public List<Transaction> getStatement(@Param("accountId") Long accountId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
