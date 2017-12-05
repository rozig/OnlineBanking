package com.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name = "close_account_request")
public class CloseAccountRequest extends Request{

    @OneToOne(mappedBy = "Account")
    private Account account;

    @Override
    public void verify() {

    }
}
