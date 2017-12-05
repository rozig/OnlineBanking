package com.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "exchange_rate")
public class ExchangeRate {

    @Id
    @GeneratedValue
    @Column(name = "Id", updatable = false, nullable = false)
    private int Id;

    @Column(name = "date", nullable = false)
    private Date date;

    @OneToMany(mappedBy = "CurrencyRate")
    private List<CurrencyRate> currencyRateList;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<CurrencyRate> getCurrencyRateList() {
        return currencyRateList;
    }

    public void setCurrencyRateList(List<CurrencyRate> currencyRateList) {
        this.currencyRateList = currencyRateList;
    }
}
