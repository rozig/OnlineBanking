package com.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "new_customer_request")
public class NewCustomerRequest {
    @Id
    @GeneratedValue
    @Column(name = "Id", updatable = false, nullable = false)
    private int Id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "email_id")
    String emailId;

    @Column(name = "mobile_num")
    String mobileNum;

    @Column(name = "date_of_birth")
    Date dateOfBirth;

    public void verify(){

    }
}
