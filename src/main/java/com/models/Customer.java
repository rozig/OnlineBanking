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
    private List<Account> accountList;

    @OneToMany(mappedBy = "Request")
    private List<Request> requestList;

    @OneToOne(mappedBy = "AccountBook")
    private AccountBook accountBook;

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }

    public AccountBook getAccountBook() {
        return accountBook;
    }

    public void setAccountBook(AccountBook accountBook) {
        this.accountBook = accountBook;
    }
}
