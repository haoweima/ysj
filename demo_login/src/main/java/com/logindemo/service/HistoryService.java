package com.logindemo.service;

import com.logindemo.domain.*;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by marnon on 2017/8/14.
 */
public interface HistoryService {
    List<Order> getOrder(String type, int employer, Date date);
    List<Goods> getGoods(int employer);
    List<Item> getGoods(String name);
    void addGoodsType(List<GoodsType> goods, int employer);
    boolean addOrder(List<Table> tables, String type, String date, Map map, String ssc);

    List<String> getInvent(int employer);
    void addInvent(int employer, String ssc);
    void getDetailsById(String idOrder, Model model);
    List<String> getGoodsNames(int employer, String ssc);
    Map<String, List> getAllGoodsNames(int employer);
    List<String> getGoodsUnit(String name);

    List<InvenTable> getInvenTable(int employer, String ssc);
    void deleteOrder(String idOrder);
}
