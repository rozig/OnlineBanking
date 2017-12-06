package com.onlinebanking.user;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.CascadeType;

import com.onlinebanking.appuser.ApplicationUser;
import com.onlinebanking.request.Request;

import java.util.Date;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private Date dateOfBirth;
    private Boolean isAdmin;

    @OneToOne(mappedBy="user")
    private ApplicationUser appUser;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_requests", joinColumns=@JoinColumn(name="user_id", referencedColumnName="id"), inverseJoinColumns =@JoinColumn(name="request_id", referencedColumnName="id"))
    private Set<Request> requests;
}