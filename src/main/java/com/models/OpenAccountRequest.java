package com.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity(name = "open_account_request")
public class OpenAccountRequest {

    @OneToOne(mappedBy = "Account")
    Account account;
}
