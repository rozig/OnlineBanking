package com.onlinebanking.customer;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.onlinebanking.account.Account;
import com.onlinebanking.user.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "customer", uniqueConstraints = {@UniqueConstraint(columnNames =
		{ "id", "email", "phoneNumber", "username"})})
public class Customer extends User {

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	@JsonIgnore
	private Set<Account> accountSet = new HashSet<Account>();

	private String isActivated;

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
}
