package com.onlinebanking.account;

import com.onlinebanking.customer.Customer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id", updatable = false, nullable = false, unique = true)
	private Long accountId;

	private String accountName;

	private double balance;

	private Date accountOpenDate;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
	private SavingAccount savingAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	private String isDefault;

	public Account(){}

	public Long getAccountId() {
		return accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Date getAccountOpenDate() {
		return accountOpenDate;
	}

	public void setAccountOpenDate(Date accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
}
