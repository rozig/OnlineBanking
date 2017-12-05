package com.models;

import java.util.List;

public class Customer extends User{
    String customerId;
    List<Account> accountList;
    List<Request> requestList;
    AccountBook accountBook;

}
