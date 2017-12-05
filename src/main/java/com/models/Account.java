package com.models;


import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int Id;

    @Column(name = "account_id", unique = true, nullable = false)
    private String accountId;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id", nullable = false)
    private int customerId;

    @Column(name = "opened_date", nullable = false)
    private Date openedDate;

    @Column(name = "balance", nullable = false)
    private double balance;

    public abstract void closeAccount();

    public Statement getStatement(){
        return null;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(Date openedDate) {
        this.openedDate = openedDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
