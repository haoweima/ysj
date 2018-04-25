package com.logindemo.domain;

import java.util.ArrayList;

/**
 * Created by marnon on 2017/8/17.
 */
public class ListComponent {
    private ArrayList<Table> table;
    private ArrayList<GoodsType> goods;
    private ArrayList<Employee> employee;

    public ArrayList<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(ArrayList<Employee> employees) {
        this.employee = employees;
    }

    public ArrayList<GoodsType> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<GoodsType> goods) {
        this.goods = goods;
    }

    public ArrayList<Table> getTable() {
        return table;
    }

    public void setTable(ArrayList<Table> table) {
        this.table = table;
    }
}
