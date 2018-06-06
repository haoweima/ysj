package com.logindemo.service.serviceImpl;

import com.logindemo.dao.HistoryDao;
import com.logindemo.domain.*;
import com.logindemo.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by marnon on 2017/8/14.
 */

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryDao historyDao;

    @Override
    public List<Order> getOrder(String type, int employer, Date date) {
        List<Order> orders;

        if(date == null){
            orders = historyDao.getOrder(type, employer);
        }else{
            orders = historyDao.getOrder(type, employer, date);
        }

        return orders;
    }

    /**
     * 通过管理员返回商品名称
     * @param employer
     * @return
     */
    @Override
    public List<Goods> getGoods(int employer) {
        List<Goods> goods;

        goods = historyDao.getGoods(employer);

        return goods;
    }

    /**
     * 通过指定商品名称返回商品
     * @param name
     * @return
     */
    @Override
    public List<Item> getGoods(String name) {
        return historyDao.getItemByName(name);
    }

    @Override
    public void addGoodsType(List<GoodsType> good, int employer) {
        for(GoodsType goods : good) {
            if(goods.getName().equals("")) break;
            historyDao.addGoods(goods, employer);
        }
    }

    /**
     * 添加订单和销售记录
     * @param tables 表单
     * @param type 订单类型
     * @param date 日期
     * @param map 用户信息
     * @param ssc 所属仓
     */
    @Override
    @Transactional
    public boolean addOrder(List<Table> tables, String type, String date, Map map, String ssc) {
        double sum = 0;
        OrderDetail detail;
        String idOrder = date.replace("-", "");
        idOrder = idOrder.replace(":","");
        idOrder = idOrder.replace(" ","");
        idOrder = idOrder.substring(2,14);

        List<String> goodsNames = historyDao.getAllGoodsNames((int)map.get("employer"));

        for(Table t : tables){
            if(t.getName().equals("")){
                break;
            }

            if(!goodsNames.contains(t.getName())){
                return false;
            }
        }

        historyDao.addOrder(idOrder, (int)map.get("employer"), (int)map.get("employee"), date, sum, type, ssc);
        for(Table table : tables){
            if(table.getName().equals("")){
                break;
            }
            int goodsId = historyDao.getGoodsIdByName(table.getName());
            //数据组装
            detail = new OrderDetail();
            detail.setIdOrder(idOrder);
            detail.setIdGoods(goodsId);
            detail.setNum(Integer.parseInt(table.getNum()));
            detail.setMoney(Double.parseDouble(table.getPrice()));
            detail.setOther(table.getOther());
            sum += Double.parseDouble(table.getPrice()) * Double.parseDouble(table.getNum());
            //新增详细销售记录
            historyDao.addDetails(detail);
            if(type.equals("sale")) {
                historyDao.saleGoods((int) map.get("employer"), goodsId, Integer.parseInt(table.getNum()), ssc);
            }else if(type.equals("come")){
                historyDao.comeGoods((int) map.get("employer"), goodsId, Integer.parseInt(table.getNum()), ssc);
            }else if(type.equals("return")){
                historyDao.comeGoods((int) map.get("employer"), goodsId, Integer.parseInt(table.getNum()), ssc);
            }else if(type.equals("change")){
                historyDao.saleGoods((int) map.get("employer"), goodsId, Integer.parseInt(table.getNum()), ssc);
            }
        }

        historyDao.updateMoneyOfOrder(idOrder, sum);

        return true;
    }

    @Override
    public List<String> getInvent(int employer) {
        List<String> inventories = historyDao.getInventory(employer);
        return inventories;
    }

    @Override
    public void addInvent(int employer, String ssc) {
        historyDao.addVent(employer, ssc);
    }

    @Override
    public void getDetailsById(String idOrder, Model model) {
        Order order = historyDao.getOrder(idOrder);
        List<Item2> items = historyDao.getItemById(idOrder);

        model.addAttribute("order", order);
        model.addAttribute("items", items);
    }

    @Override
    public List<String> getGoodsNames(int employer, String ssc) {

        return historyDao.getGoodsNames(employer, ssc);
    }

    @Override
    public Map<String, List> getAllGoodsNames(int employer) {
        return historyDao.getGoodsNames(employer);
    }

    @Override
    public List<String> getGoodsUnit(String name) {
        return historyDao.getGoodsUnit(name);
    }

    @Override
    public List<InvenTable> getInvenTable(int employer, String ssc) {
        return historyDao.getInvenTable(employer, ssc);
    }

    @Override
    public void deleteOrder(String idOrder) {
        historyDao.deleteOrder(idOrder);
    }


}
