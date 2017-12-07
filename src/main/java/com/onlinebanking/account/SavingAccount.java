package com.onlinebanking.account;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "saving_account")
public class SavingAccount{
	@GenericGenerator(name = "generator", strategy = "foreign",
			parameters = @Parameter(name = "property", value = "account"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "account_id", unique = true, nullable = false)
	private Long accountId;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
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
