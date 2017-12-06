package com.onlinebanking.request;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.onlinebanking.user.User;

import java.util.Set;

@Entity
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String type;
	private String status;

	@ManyToMany(mappedBy="requests")
	private Set<User> users;
}
