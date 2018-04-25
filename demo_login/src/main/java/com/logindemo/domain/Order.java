package com.logindemo.domain;

import java.util.Date;

/**
 * Created by marnon on 2017/8/14.
 */
public class Order{
    private String idOrder;
    private Date date;
    private String name;
    private double receivables;
    private double income;
    private String type;
    private String ssc;

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getReceivables() {
        return receivables;
    }

    public void setReceivables(double receivables) {
        this.receivables = receivables;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSsc() {
        return ssc;
    }

    public void setSsc(String ssc) {
        this.ssc = ssc;
    }
}
