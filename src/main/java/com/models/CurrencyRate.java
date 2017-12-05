package com.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "currency_rate")
public class CurrencyRate {

    @Id
    @GeneratedValue
    @Column(name = "Id", updatable = false, nullable = false)
    private int Id;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "buy_rate", nullable = false)
    private double buyRate;

    @Column(name = "sell_rate", nullable = false)
    private double sellRate;

    @Column(name = "date", nullable = false)
    private Date date;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(double buyRate) {
        this.buyRate = buyRate;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
