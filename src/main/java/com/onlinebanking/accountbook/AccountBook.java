package com.onlinebanking.accountbook;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.onlinebanking.customer.Customer;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table( name = "account_book", uniqueConstraints = {@UniqueConstraint(columnNames =
		{ "customer_id" })})
public class AccountBook {
	@Id
	@GeneratedValue
	@Column(unique = true, updatable = false, nullable = false)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private Customer customer;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accountBook")
	private Set<Item> items;

	public Long getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}
}
