package com.onlinebanking.accountbook;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.onlinebanking.account.Account;

import javax.persistence.*;

@Entity
@Table(name = "account_book_item")
public class Item {

	@Id
	@GeneratedValue
	@Column(unique = true, updatable = false, nullable = false)
	private Long id;

	private String itemName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="accountId")
	@JsonIdentityReference(alwaysAsId=true)
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_book_id", nullable = false)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private AccountBook accountBook;

	public Long getId() {
		return id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public AccountBook getAccountBook() {
		return accountBook;
	}

	public void setAccountBook(AccountBook accountBook) {
		this.accountBook = accountBook;
	}
}
