package com.models;

import javax.persistence.Entity;

@Entity(name = "checking_account")
public class CheckingAccount extends Account{
    @Override
    public void closeAccount() {

    }

    @Override
    public Statement getStatement() {
        return super.getStatement();
    }
}
