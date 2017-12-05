package com.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "admin")
public class Admin extends User{
    @OneToMany(mappedBy = "Request")
    List<Request> requestList;

    @OneToMany(mappedBy = "NewCustomerRequest")
    List<NewCustomerRequest> newCustomerRequestList;
}
