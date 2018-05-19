package com.logindemo.dao;

import com.logindemo.domain.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by marnon on 2017/8/14.
 */
public interface HistoryDao {

    /**
     * 某类型的全部订单
     * @param type
     * @param employer
     * @return
     */
    List<Order> getOrder(String type, int employer);

    /**
     * 某日期某类型的全部订单
     * @param type
     * @param employer
     * @param date
     * @return
     */
    List<Order> getOrder(String type, int employer, Date date);

    Order getOrder(String idOrder);

    List<Order> getReportOrder(String date);
    int addOrder(String idOrder, int employer, int employee, String date, double income, String type, String ssc);
    void deleteOrder(String idOrder);
    void updateMoneyOfOrder(String idOrder, double income);

    int getGoodsIdByName(String name);
    List<Goods> getGoodsByName(String name);
    List<String> getGoodsNames(int employer, String ssc);
    List<String> getAllGoodsNames(int employer);
    Map<String, List> getGoodsNames(int employer);
    List<Goods> getGoods(int employer);
    void addGoods(GoodsType goods, int employer);
    void updateGoods(int idGoods, String name, String unit, double come_price, double sale_price, String other);
    void deleteGoods(int idGoods);
    List<String> getGoodsUnit(String name);

    List<OrderDetail> getDetails(String idOrder);
    List<Item2> getItemById(String idOrder);
    List<Item> getItemByName(String name);
    void addDetails(OrderDetail detail);
    void deleteDetails(int idOrder);

    List<String> getInventory(int employer);
    void addInventory(Inventory inventory);
    List<InvenTable> getInvenTable(int employer, String ssc);
    void addVent(int employer, String ssc);

    void saleGoods(int employer, int idGoods, int num, String ssc);
    void comeGoods(int employer, int idGoods, int num, String ssc);


}
