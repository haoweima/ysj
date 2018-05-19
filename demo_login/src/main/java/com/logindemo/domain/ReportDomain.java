package com.logindemo.domain;

import java.util.List;

/**
 * Created by marnon on 2018/5/17.
 */
public class ReportDomain {

    private String saleMoney;
    private String comeMoney;
    private String returnMoney;
    private String changeMoney;
    private List<String> hotGoods;
    private List<String> profitGoods;

    public String getSaleMoney() {
        return saleMoney;
    }

    public void setSaleMoney(String saleMoney) {
        this.saleMoney = saleMoney;
    }

    public String getComeMoney() {
        return comeMoney;
    }

    public void setComeMoney(String comeMoney) {
        this.comeMoney = comeMoney;
    }

    public String getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(String returnMoney) {
        this.returnMoney = returnMoney;
    }

    public String getChangeMoney() {
        return changeMoney;
    }

    public void setChangeMoney(String changeMoney) {
        this.changeMoney = changeMoney;
    }

    public List<String> getHotGoods() {
        return hotGoods;
    }

    public void setHotGoods(List<String> hotGoods) {
        this.hotGoods = hotGoods;
    }

    public List<String> getProfitGoods() {
        return profitGoods;
    }

    public void setProfitGoods(List<String> profitGoods) {
        this.profitGoods = profitGoods;
    }
}
