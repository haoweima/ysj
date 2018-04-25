package com.logindemo.domain;

/**
 * Created by marnon on 2017/8/16.
 */
public class Goods {
    private int idGoods;
    private int employer;
    private String name;
    private String unit;
    private double come_price;
    private double sale_price;
    private String other;

    public int getIdGoods() {
        return idGoods;
    }

    public void setIdGoods(int idGoods) {
        this.idGoods = idGoods;
    }

    public int getEmployer() {
        return employer;
    }

    public void setEmployer(int employer) {
        this.employer = employer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getCome_price() {
        return come_price;
    }

    public void setCome_price(double come_price) {
        this.come_price = come_price;
    }

    public double getSale_price() {
        return sale_price;
    }

    public void setSale_price(double sale_price) {
        this.sale_price = sale_price;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
