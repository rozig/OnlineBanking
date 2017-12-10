package com.onlinebanking.currencyrate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long>{
	@Query("select a from CurrencyRate a where " +
			"a.date = (select max(b.date) from CurrencyRate b where a.currencyCode = b.currencyCode)")
	public List<CurrencyRate> findLatest();
}
