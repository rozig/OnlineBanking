package com.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name = "close_account_request")
public class CloseAccountRequest {

    @OneToOne(mappedBy = "Account")
    Account account;
}
