package com.models;

import javax.persistence.*;
import java.util.Date;


@Entity(name="Saving_account")
public class SavingAccount extends Account{
    @Column(name = "maturity_date", nullable = false)
    private Date maturityDate;

    @Column(name = "interest_rate", nullable = false)
    private double interestRate;

    public SavingAccount(){
        super();
    }

    public double calculateAccruedInterest(){
        return 0.0;
    }

    @Override
    public void closeAccount() {

    }

    @Override
    public Statement getStatement() {
        return super.getStatement();
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
