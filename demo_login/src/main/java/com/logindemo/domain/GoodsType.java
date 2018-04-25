package com.logindemo.domain;

/**
 * Created by marnon on 2017/8/31.
 */
public class GoodsType {
    private String name;
    private String unit;
    private String come_price;
    private String sale_price;
    private String other;

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

    public String getCome_price() {
        return come_price;
    }

    public void setCome_price(String come_price) {
        this.come_price = come_price;
    }

    public String getSale_price() {
        return sale_price;
    }

    public void setSale_price(String sale_price) {
        this.sale_price = sale_price;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
