package com.onlinebanking.account;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "saving_account")
public class SavingAccount{
	@Id
	@GeneratedValue
	@Column(name = "saving_account_id",updatable = false, unique = true, nullable = false)
	private Long accountId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	private Date maturityDate;

	private double interestRate;

	public double calculateAccruedInterest(){
		return 0.0;
	}

	public Long getAccountId() {
		return accountId;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
