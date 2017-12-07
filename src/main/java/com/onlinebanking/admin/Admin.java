package com.onlinebanking.admin;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import com.onlinebanking.user.User;

@Entity
public class Admin extends User {
}