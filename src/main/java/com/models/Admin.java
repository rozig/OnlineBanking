package com.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "admin")
public class Admin extends User{
    @OneToMany(mappedBy = "Request")
    private List<Request> requestList;

    @OneToMany(mappedBy = "NewCustomerRequest")
    private List<NewCustomerRequest> newCustomerRequestList;

    @OneToMany(mappedBy = "Request")
    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }

    public List<NewCustomerRequest> getNewCustomerRequestList() {
        return newCustomerRequestList;
    }

    public void setNewCustomerRequestList(List<NewCustomerRequest> newCustomerRequestList) {
        this.newCustomerRequestList = newCustomerRequestList;
    }
}
