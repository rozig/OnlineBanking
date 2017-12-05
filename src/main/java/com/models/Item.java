package com.models;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Item {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_book_id", nullable = false)
    String accountBookId;

    @Column(name = "item_name", nullable = false)
    String itemName;

    @Column(name = "account_id", nullable = false)
    String accountId;

    @Column(name = "account_name", nullable = false)
    String accountName;
}
