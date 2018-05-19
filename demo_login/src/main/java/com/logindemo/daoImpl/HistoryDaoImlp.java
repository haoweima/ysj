package com.logindemo.daoImpl;

import com.logindemo.dao.HistoryDao;
import com.logindemo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by marnon on 2017/8/14.
 */

@Repository
public class HistoryDaoImlp implements HistoryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 某类型的全部订单
     * @param type
     * @param employer
     * @return
     */
    @Override
    public List<Order> getOrder(String type, int employer) {
        String sql = "select Order.idOrder as idOrder," +
                "Order.date as date, " +
                "User.name as name," +
                "Order.income as income," +
                "Order.type as type " +
                "from ysj_Manager.Order INNER JOIN ysj_Manager.User on Order.employee = User.idUser where Order.employer = ? and Order.type = ?";
        List<Order> list = jdbcTemplate.query(sql, new Object[]{employer, type}, new BeanPropertyRowMapper(Order.class));
        if(null != list && list.size()>0){
            return list;
        }else{
            return null;
        }
    }


    /**
     * 某日期某类型的全部订单
     * @param type
     * @param employer
     * @param date
     * @return
     */
    @Override
    public List<Order> getOrder(String type, int employer, Date date) {
        String sql = "select Order.idOrder as idOrder," +
                "Order.date as date, " +
                "User.name as name," +
                "Order.income as income," +
                "Order.type as type " +
                "from ysj_Manager.Order INNER JOIN ysj_Manager.User on Order.employee = User.idUser where Order.employer = ? and Order.type = ? and date = ?";
        List<Order> list = jdbcTemplate.query(sql, new Object[]{employer, type, date}, new BeanPropertyRowMapper(Order.class));
        if(null != list && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

    @Override
    public Order getOrder(String idOrder) {
        String sql = "select Order.idOrder as idOrder," +
                "Order.date as date, " +
                "User.name as name," +
                "Order.income as income," +
                "Order.type as type, " +
                "Order.ssc as ssc " +
                "from ysj_Manager.Order INNER JOIN ysj_Manager.User on Order.employee = User.idUser where Order.idOrder = ?";
        List<Order> list = jdbcTemplate.query(sql, new Object[]{idOrder}, new BeanPropertyRowMapper(Order.class));
        if(null != list && list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<Order> getReportOrder(String date) {
        String sql = "SELECT employer, sum(income) income, type FROM `Order` where date LIKE '? %' GROUP BY employer, type;";

        List<Order> list = jdbcTemplate.query(sql, new Object[]{date}, new BeanPropertyRowMapper<>(Order.class));

        if(null != list && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

    /**
     * 添加订单
     * @param employer
     * @param employee
     * @param date
     * @param income
     * @param type
     * @return
     */
    @Override
    public int addOrder(String idOrder, int employer, int employee, String date, double income, String type, String ssc) {
        String sql = "insert into ysj_Manager.Order(idOrder, employer, date, employee, income, type, ssc) value (?, ?, ?, ?, ?, ?, ?)";
        int temp = jdbcTemplate.update(sql, idOrder, employer, date, employee, income, type, ssc);

        return -1;
    }

    /**
     * 删除订单
     * @param idOrder
     */
    @Override
    public void deleteOrder(String idOrder) {
        jdbcTemplate.update("update ysj_Manager.Order set type = concat('deleted ',type) where idOrder = " + idOrder);
    }

    @Override
    public void updateMoneyOfOrder(String idOrder, double income) {
        jdbcTemplate.update("UPDATE ysj_Manager.Order set income = ? where idOrder = ?", new Object[]{income, idOrder});
    }

    @Override
    public int getGoodsIdByName(String name) {
        return jdbcTemplate.queryForObject("select idGoods from ysj_Manager.Goods where name = ?",new Object[]{name}, Integer.class);
    }

    @Override
    public List<Goods> getGoodsByName(String name) {
        return jdbcTemplate.query("select * from ysj_Manager.Goods where name = ?", new Object[]{name}, new BeanPropertyRowMapper(Goods.class));
    }

    @Override
    public List<String> getGoodsNames(int employer, String ssc) {
        String sql = "select name from Goods where idGoods in (" +
                "select idGoods from Inventory where employer = ? and ssc = ?" +
                ")";
        List<String> names = jdbcTemplate.queryForList(sql, new Object[]{employer, ssc}, String.class);

        return names;
    }

    @Override
    public List<String> getAllGoodsNames(int employer){
        List<String> result;

        String sql = "select name from Goods where employer = ?";

        result = jdbcTemplate.queryForList(sql, new Object[]{employer}, String.class);
        return result;
    }

    @Override
    public Map<String, List> getGoodsNames(int employer) {
        Map<String, List> result = new HashMap<>();

        List<String> names = jdbcTemplate.queryForList("select name from Goods where employer = ?", new Object[]{employer}, String.class);
        List<String> units = jdbcTemplate.queryForList("select unit from Goods where employer = ?", new Object[]{employer}, String.class);

        result.put("names", names);
        result.put("units", units);
        return result;
    }

    /**
     * 获得某雇主的所有商品类型
     * @param employer
     * @return
     */
    @Override
    public List<Goods> getGoods(int employer) {
        List<Goods> list = jdbcTemplate.query("select * from ysj_Manager.Goods where employer = ?", new Object[]{employer}, new BeanPropertyRowMapper(Goods.class));
        if(null != list && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

    /**
     * 添加商品
     * @param goods
     */
    @Override
    public void addGoods(GoodsType goods, int employer) {
        String sql = "insert into ysj_Manager.Goods(idGoods, employer, name, unit, come_price, sale_price, other) VALUE (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, null, employer, goods.getName(), goods.getUnit(), goods.getCome_price(), goods.getSale_price(), goods.getOther());
    }

    /**
     * 编辑商品 管理员操作
     * @param idGoods
     * @param name
     * @param unit
     * @param come_price
     * @param sale_price
     * @param other
     */
    @Override
    public void updateGoods(int idGoods, String name, String unit, double come_price, double sale_price, String other) {
        String sql = "update ysj_Manager.Goods set name = ?, unit = ?, come_price = ?, sale_price = ?, ohter = ? where idGoods = ?";
        jdbcTemplate.update(sql, name, unit, come_price, sale_price, other, idGoods);
    }

    /**
     * 删除商品 管理员操作
     * @param idGoods
     */
    @Override
    public void deleteGoods(int idGoods) {
        String sql = "delete from ysj_Manager.Goods where idGoods = " + idGoods;
        jdbcTemplate.update(sql);
    }

    @Override
    public List<String> getGoodsUnit(String name) {
        List<String> list = jdbcTemplate.queryForList("select unit from ysj_Manager.Goods where name = ?", new Object[]{name}, String.class);

        return list;
    }

    /**
     * 获得订单详情
     * @param idOrder
     * @return
     */
    @Override
    public List<OrderDetail> getDetails(String idOrder) {
        List<OrderDetail> list = jdbcTemplate.query("select * from ysj_Manager.Detail where idOrder = " + idOrder, new BeanPropertyRowMapper(OrderDetail.class));

        if(list != null && list.size() > 0){
            return list;
        }else return null;
    }

    @Override
    public List<Item2> getItemById(String idOrder) {
        String sql =" select Goods.`name` as 'name'," +
                "Goods.unit as unit," +
                "Detail.num as num," +
                "Detail.money as price," +
                "Detail.money * Detail.num as sum," +
                "Detail.other as other " +
                "from Goods inner join Detail on Goods.idGoods = Detail.idGoods where idOrder = ?";

        List<Item2> items = jdbcTemplate.query(sql, new Object[]{idOrder}, new BeanPropertyRowMapper(Item2.class));

        if(items != null && items.size() > 0){
            return items;
        }
        else return null;
    }

    @Override
    public List<Item> getItemByName(String name) {
        String sql =" select Goods.`name` as 'name'," +
                "Goods.unit as unit," +
                "Inventory.num as num," +
                "Goods.sale_price as price," +
                "Goods.other as other " +
                "from Goods inner join Inventory on Goods.idGoods = Inventory.idGoods where Goods.name = ?";

        List<Item> items = jdbcTemplate.query(sql, new Object[]{name}, new BeanPropertyRowMapper(Item.class));

        if(items != null && items.size() > 0){
            return items;
        }
        else return null;
    }

    /**
     * 添加订单详情
     * @param detail
     */
    @Override
    public void addDetails(OrderDetail detail) {
        String sql = "insert into ysj_Manager.Detail(iddetail, idOrder, idGoods, num, money, other) value(?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, null, detail.getIdOrder(), detail.getIdGoods(), detail.getNum(), detail.getMoney(), detail.getOther());
    }

    /**
     * 删除订单详情
     * @param idOrder
     */
    @Override
    public void deleteDetails(int idOrder) {
        jdbcTemplate.update("delete FROM  ysj_Manager.Detail where idOrder = " + idOrder);
    }

    /**
     * 查询仓库详情
     * @param employer
     * @return
     */
    @Override
    public List<String> getInventory(int employer) {
        List<String> inventories = jdbcTemplate.queryForList("select ssc from ysj_Manager.Inventory where employer = ?  GROUP BY ssc" , new Object[]{employer}, String.class);

        if(inventories != null && inventories.size() > 0) {
            return inventories;
        }else
            return null;
    }

    /**
     * 添加库存信息
     * @param inventory
     */
    @Override
    public void addInventory(Inventory inventory) {
        String sql = "insert into ysj_Manager.Inventory(idinventory, employer, idGoods, num, ssc) value (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, null, inventory.getEmployer(), inventory.getIdGoods(), inventory.getNum(), inventory.getSsc());
    }

    @Override
    public List<InvenTable> getInvenTable(int employer, String ssc) {
        String sql = "select Goods.name as name," +
                "Inventory.ssc as ssc," +
                "Inventory.num as num," +
                "Goods.come_price as come," +
                "Goods.sale_price as sale " +
                "from ysj_Manager.Inventory inner join ysj_Manager.Goods on Inventory.idGoods = Goods.idGoods " +
                "where Inventory.ssc = ? and Inventory.employer = ?";

        return jdbcTemplate.query(sql, new Object[]{ssc, employer}, new BeanPropertyRowMapper(InvenTable.class));
    }

    @Override
    public void addVent(int employer, String ssc) {
        String sql = "insert into ysj_Manager.Inventory(idinventory, employer, idGoods, num, ssc) value (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, null, employer, null, null, ssc);
    }

    /**
     * 商品出库
     * @param employer
     * @param idGoods
     * @param n
     */
    @Override
    public void saleGoods(int employer, int idGoods, int n, String ssc) {
        String sql = "update ysj_Manager.Inventory set num = num - " + n + " where employer = " + employer + " and idGoods = " + idGoods + " and ssc = ?";

        jdbcTemplate.update(sql, ssc);
    }

    /**
     * 商品入库
     * @param employer
     * @param idGoods
     * @param n
     */
    @Override
    public void comeGoods(int employer, int idGoods, int n, String ssc) {
        String sql = "update ysj_Manager.Inventory set num = num + " + n + " where employer = " + employer + " and idGoods = " + idGoods + " and ssc = ?";

        int temp = jdbcTemplate.update(sql, ssc);
        if (temp == 0) {
            jdbcTemplate.update("insert into ysj_Manager.Inventory(idinventory, employer, idGoods, num, ssc) value (?, ?, ?, ?, ?)", null, employer, idGoods, n, ssc);
        }
    }

}
