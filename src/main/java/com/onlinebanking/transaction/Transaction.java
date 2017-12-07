package com.onlinebanking.transaction;

import com.onlinebanking.account.Account;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue
	@Column(name = "transaction_id", unique = true, updatable = false, nullable = false)
	private Long transactionId;

	private double amount;

	@ManyToOne
	@PrimaryKeyJoinColumn
	private Account creditAccount;

	@ManyToOne
	@PrimaryKeyJoinColumn
	private Account debitAccount;

	private String channelId;

	private Date transactionDate;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Account getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(Account creditAccount) {
		this.creditAccount = creditAccount;
	}

	public Account getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(Account debitAccount) {
		this.debitAccount = debitAccount;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Long getTransactionId() {
		return transactionId;
	}
}
