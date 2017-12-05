package com.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Statement {
    @Id
    @GeneratedValue
    @Column(name = "Id", updatable = false, nullable = false)
    private int Id;

    @OneToOne(mappedBy = "Account")
    private String accountId;

    @Column(name = "from_date", nullable = false)
    private Date fromDate;

    @Column(name = "to_date", nullable = false)
    private Date toDate;

    @OneToMany(mappedBy = "Transaction")
    private List<Transaction> transactionList;
}
