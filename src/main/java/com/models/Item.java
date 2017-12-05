package com.models;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Item {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_book_id", nullable = false)
    private String accountBookId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    public String getAccountBookId() {
        return accountBookId;
    }

    public void setAccountBookId(String accountBookId) {
        this.accountBookId = accountBookId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
