package com.logindemo.domain;

/**
 * Created by marnon on 2017/8/20.
 */
public class Inventory {
    private int idinventory;
    private int employer;
    private int idGoods;
    private int num;
    private String ssc;

    public int getIdinventory() {
        return idinventory;
    }

    public void setIdinventory(int idinventory) {
        this.idinventory = idinventory;
    }

    public int getEmployer() {
        return employer;
    }

    public void setEmployer(int employer) {
        this.employer = employer;
    }

    public int getIdGoods() {
        return idGoods;
    }

    public void setIdGoods(int idGoods) {
        this.idGoods = idGoods;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSsc() {
        return ssc;
    }

    public void setSsc(String ssc) {
        this.ssc = ssc;
    }
}
