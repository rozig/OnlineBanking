package com.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name = "open_account_request")
public class OpenAccountRequest extends Request{

    @OneToOne(mappedBy = "Account")
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public void verify() {

    }
}
