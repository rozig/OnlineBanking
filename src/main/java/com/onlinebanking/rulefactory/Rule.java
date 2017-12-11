package com.onlinebanking.rulefactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onlinebanking.customer.Customer;

import javax.persistence.*;

@Entity
public class Rule {

	@Id
	@GeneratedValue
	private Long id;

	private Double maxTranLimit;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "rule")
	@JsonIgnore
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	private Rule(){}

	public static Rule generateRule(Customer customer){
		Rule rule = new Rule();
		rule.setCustomer(customer);

//		calculating daily tran limit based on creditscore and monthlyIncome
		rule.setMaxTranLimit(customer.getCreditScore() * 0.2 * 1000 + customer.getMonthlyIncome() * 0.1);
		return rule;
	}

	public Double getMaxTranLimit() {
		return maxTranLimit;
	}

	void setMaxTranLimit(Double dailyTranLimit) {
		this.maxTranLimit = dailyTranLimit;
	}

	public Customer getCustomer() {
		return customer;
	}

	void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
