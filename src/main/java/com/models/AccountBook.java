package com.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class AccountBook {
    @Id
    @GeneratedValue
    @Column(name="Id", updatable = false, nullable = false)
    private int Id;

    @OneToOne()
    String customerId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Item")
    List<Item> itemList;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
