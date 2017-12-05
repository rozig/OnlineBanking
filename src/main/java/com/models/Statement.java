package com.models;

import java.util.Date;
import java.util.List;

public class Statement {
    String accountId;
    Date fromDate;
    Date toDate;
    List<Transaction> transactionList;
}
