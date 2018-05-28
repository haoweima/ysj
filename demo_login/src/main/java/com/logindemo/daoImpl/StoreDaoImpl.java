package com.logindemo.daoImpl;

import com.logindemo.dao.StoreDao;
import com.logindemo.domain.Goods;
import org.springframework.beans.factory.annotation.Autowired;
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
        String sql = "select sum(income) from Order where employer = ? and date like '? %' and type = ?";

        double result = jdbcTemplate.queryForObject(sql, new Object[]{employer, date, type}, Double.class);

        return result;
    }

    //获取某个用户某天的热门商品
    @Override
    public List<Integer> getHotGoodsNums(int employer, String date) {
        String sql = "select idGoods from Detail where idOrder in " +
                "(SELECT idOrder from Order where employer = ? and date like '? %') " +
                "group by idGoods order by sum(num) desc";

        List<Integer> goodsList = jdbcTemplate.queryForList(sql, new Object[]{employer, date}, Integer.class);

        return goodsList;
    }

    //获取某个用户某天的热销商铺
    @Override
    public List<Integer> getHotGoodsSale(int employer, String date) {
        String sql = "select idGoods from Detail where idOrder in " +
                "(SELECT idOrder from Order where employer = ? and date like '? %')" +
                " group by idGoods order by sum(money) desc";

        List<Integer> goodsList = jdbcTemplate.queryForList(sql, new Object[]{employer, date}, Integer.class);

        return goodsList;
    }

    //从一堆商铺中找到销售量最多的几个
    @Override
    public List<Goods> getHotGoodsById(String goodsId) {
        String sql = "select * from Goods where idGoods in (" +
                "select idGoods from Detail where idGoods in(?) group by idGoods ORDER BY sum(num) desc)";

        List<Goods> goodsList = jdbcTemplate.queryForList(sql, new Object[]{goodsId}, Goods.class);

        return goodsList;
    }

    //从商品中找进价最低的商品
    @Override
    public List<Goods> getLowSaleGoodsByUserId(int employer) {
        String sql = "select * from Goods where employer = ? and come_price != 0 ORDER BY come_price limit 10";

        List<Goods> goodsList = jdbcTemplate.queryForList(sql, new Object[]{employer}, Goods.class);

        return goodsList;
    }
}
