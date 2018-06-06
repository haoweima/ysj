package com.logindemo.dao;

import com.logindemo.domain.Goods;
import com.logindemo.domain.OrderDetail;

import java.util.List;

/**
 * Created by marnon on 2018/5/22.
 */
public interface StoreDao {

    //获取每一天的某种类型的盈利额总数
    double getSumOfDay(int employer, String date, String type);

    //获取某个用户某天的热门商品
    List<Integer> getHotGoodsNums(int employer, String date);

    //获取某个用户某天的热销商铺
    List<Integer> getHotGoodsSale(int employer, String date);

    //从一堆商铺中找到销售量最多的几个
    List<Goods> getHotGoodsById(String goodsId);

    //从商品中找进价最低的商品
    List<Goods> getLowSaleGoodsByUserId(int employer);

    //查询商品的详细销售情况
    List<OrderDetail> getDetailByIds(String goodsId, String dateS, String dateE);
}
