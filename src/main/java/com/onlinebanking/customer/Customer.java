package com.onlinebanking.customer;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.onlinebanking.account.Account;
import com.onlinebanking.user.User;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Customer extends User {

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	@JsonIgnore
	private Set<Account> accountSet = new HashSet<Account>();

	public Set<Account> getAccountSet() {
		return accountSet;
	}

	public void setAccountSet(Set<Account> accountSet) {
		this.accountSet = accountSet;
	}
}
