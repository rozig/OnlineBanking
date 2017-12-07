package com.onlinebanking.account;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account")
public abstract class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id", updatable = false, nullable = false, unique = true)
	private Long accountId;

	private String accountName;

	private double balance;

	private Date accountOpenDate;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private SavingAccount savingAccount;

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
}
