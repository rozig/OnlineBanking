package com.onlinebanking.customer;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.onlinebanking.account.Account;
import com.onlinebanking.rulefactory.Rule;
import com.onlinebanking.user.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "customer", uniqueConstraints = {@UniqueConstraint(columnNames =
		{ "id", "email", "phoneNumber", "ssn"})})
public class Customer extends User {

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private Set<Account> accountSet = new HashSet<Account>();

	@Column(name = "ssn", unique = true, nullable = false)
	private String ssn;

	private String isActivated;

	@OneToOne
	private Rule rule;

	public Set<Account> getAccountSet() {
		return accountSet;
	}

	public void setAccountSet(Set<Account> accountSet) {
		this.accountSet = accountSet;
	}

	public String getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(String isActivated) {
		this.isActivated = isActivated;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}
}
