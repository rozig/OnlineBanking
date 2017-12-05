package com.models;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue
    @Column(name = "transaction_id", updatable = false, nullable = false)
    private String transactionId;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "channel_id", nullable = false)
    private ChannelId channelId;

    @OneToOne(mappedBy = "Account")
    private Account account;

    @Column(name = "credit_id", nullable = false)
    private String creditId;

    public void doTransaction(){

    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ChannelId getChannelId() {
        return channelId;
    }

    public void setChannelId(ChannelId channelId) {
        this.channelId = channelId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }
}
