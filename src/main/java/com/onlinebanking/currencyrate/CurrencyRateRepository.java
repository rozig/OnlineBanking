package com.onlinebanking.currencyrate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long>{
	@Query("select distinct a.currencyCode, a from CurrencyRate a order by a.date desc ")
	public List<CurrencyRate> findLatest();
}
