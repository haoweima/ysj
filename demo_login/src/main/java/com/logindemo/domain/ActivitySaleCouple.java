package com.logindemo.domain;

/**
 * Created by marnon on 2018/6/6.
 */
public class ActivitySaleCouple {
    private String nameFirst;
    private double saleOld;
    private String nameSecond;
    private double saleNew;
    private double saleMoney;
    private double profit;

    public String getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public String getNameSecond() {
        return nameSecond;
    }

    public void setNameSecond(String nameSecond) {
        this.nameSecond = nameSecond;
    }

    public double getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(double saleMoney) {
        this.saleMoney = saleMoney;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getSaleOld() {
        return saleOld;
    }

    public void setSaleOld(double saleOld) {
        this.saleOld = saleOld;
    }

    public double getSaleNew() {
        return saleNew;
    }

    public void setSaleNew(double saleNew) {
        this.saleNew = saleNew;
    }
}
