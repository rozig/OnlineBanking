package com.models;

import java.util.Date;

public abstract class Account {
    String accountId;
    String accountName;
    String customerId;
    Date openedDate;
    double balance;

    public abstract void closeAccount();

    public Statement getStatement(){
        return null;
    }
}
