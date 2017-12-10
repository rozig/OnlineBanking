package com.onlinebanking.currencyrate;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "currency_rate", uniqueConstraints = {@UniqueConstraint(columnNames =
		{ "id", "currencyCode", "date"})})
public class CurrencyRate {

	@Id
	@GeneratedValue
	@Column(unique = true, updatable = false, nullable = false)
	private Long id;

	private String currencyCode;

	private double saleRate;

	private double buyRate;

	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date date;

	public Long getId() {
		return id;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getSaleRate() {
		return saleRate;
	}

	public void setSaleRate(double saleRate) {
		this.saleRate = saleRate;
	}

	public double getBuyRate() {
		return buyRate;
	}

	public void setBuyRate(double buyRate) {
		this.buyRate = buyRate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
