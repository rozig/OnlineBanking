package com.models;

import javax.persistence.*;
import java.util.List;

@Entity(name = "customer")
public class Customer extends User{

    @Id
    @GeneratedValue
    @Column(name = "Id", updatable = false, nullable = false)
    private int customerId;

    @OneToMany(mappedBy = "Account")
    List<Account> accountList;

    @OneToMany(mappedBy = "Request")
    List<Request> requestList;


    AccountBook accountBook;

}
