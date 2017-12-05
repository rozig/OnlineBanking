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

}
