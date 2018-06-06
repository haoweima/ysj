package com.logindemo.dao.daoImpl;

import com.logindemo.dao.StoreDao;
import com.logindemo.domain.Goods;
import com.logindemo.domain.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by marnon on 2018/5/22.
 */
@Repository
public class StoreDaoImpl implements StoreDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //获取每一天的某种类型的盈利额总数
    @Override
    public double getSumOfDay(int employer, String date, String type) {
        String sql = "select sum(income) from `Order` where employer = " + employer +
                " and date like '" + date +
                " %' and type = '" + type + "'";

        Double result = jdbcTemplate.queryForObject(sql, new Object[]{}, Double.class);

        if(result == null){
            return 0;
        }
        return result;
    }

    //获取某个用户某天的热门商品
    @Override
    public List<Integer> getHotGoodsNums(int employer, String date) {
        String sql = "select idGoods from Detail where idOrder in " +
                "(SELECT idOrder from `Order` where employer = " + employer +
                " and date like '" + date +
                " %') " +
                "group by idGoods order by sum(num) desc";

        List<Integer> goodsList = jdbcTemplate.queryForList(sql, new Object[]{}, Integer.class);

        return goodsList;
    }

    //获取某个用户某天的热销商铺
    @Override
    public List<Integer> getHotGoodsSale(int employer, String date) {
        String sql = "select idGoods from Detail where idOrder in " +
                "(SELECT idOrder from `Order` where employer = " + employer +
                " and date like '" + date +
                " %')" +
                " group by idGoods order by sum(money) desc";

        List<Integer> goodsList = jdbcTemplate.queryForList(sql, new Object[]{}, Integer.class);

        return goodsList;
    }

    //从一堆商铺中找到销售量最多的几个
    @Override
    public List<Goods> getHotGoodsById(String goodsId) {
        String sql = "select * from Goods where idGoods in (" +
                "select idGoods from Detail where idGoods in(" + goodsId +
                ") group by idGoods ORDER BY sum(num) desc)";

        List<Goods> goodsList = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper(Goods.class));

        return goodsList;
    }

    //从商品中找进价最低的商品
    @Override
    public List<Goods> getLowSaleGoodsByUserId(int employer) {
        String sql = "select * from Goods where employer = " + employer +
                " and come_price != 0 and idGoods not in (" +
                "select idGoods from Detail where idOrder in (select idOrder from `Order` where type = 'sale' and employer = " + employer +
                "))" +
                "ORDER BY come_price limit 10";

        List<Goods> goodsList = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(Goods.class));

        return goodsList;
    }

    @Override
    public List<OrderDetail> getDetailByIds(String goodsId, String dateS, String dateE) {
        String sql = "select idGoods, sum(num) num, sum(money * num) money from `Detail` where idGoods in (" + goodsId +
                ") and idOrder in (" +
                "select idOrder from `Order` where DATE_FORMAT(date, '%Y%m%d') <= '" + dateE +
                "' and DATE_FORMAT(date, '%Y%m%d') >= '" + dateS + "' )" +
                "GROUP BY idGoods";

        List<OrderDetail> result = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper(OrderDetail.class));

        return result;
    }
}
