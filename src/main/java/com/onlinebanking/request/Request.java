package com.onlinebanking.request;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.onlinebanking.account.Account;
import com.onlinebanking.admin.Admin;
import com.onlinebanking.customer.Customer;
import com.onlinebanking.user.User;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "request")
public class Request {
	@Id
	@GeneratedValue
	@Column(name = "request_id", unique = true, updatable = false, nullable = false)
	private Long id;

	@Column(name = "type", updatable = false, nullable = false)
	private RequestType type;

	@Column(name = "status", updatable = true, nullable = false)
	private RequestStatus status;

	@ManyToOne
	@PrimaryKeyJoinColumn
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="accountId")
	@JsonIdentityReference(alwaysAsId=true)
	private Account account;

	@ManyToOne
	@PrimaryKeyJoinColumn
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private Customer customer;

	@ManyToOne
	@PrimaryKeyJoinColumn
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private Admin admin;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;

	public Long getId() {
		return id;
	}

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
