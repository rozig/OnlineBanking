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
    @JoinColumn(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "opened_date", nullable = false)
    private Date openedDate;

    @Column(name = "balance", nullable = false)
    private double balance;

    public abstract void closeAccount();

    public Statement getStatement(){
        return null;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setOpenedDate(Date openedDate) {
        this.openedDate = openedDate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
