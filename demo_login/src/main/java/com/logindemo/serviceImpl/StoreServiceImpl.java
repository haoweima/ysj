package com.logindemo.serviceImpl;

import com.logindemo.Utils.DateUtil;
import com.logindemo.Utils.RedisUtil;
import com.logindemo.dao.HistoryDao;
import com.logindemo.dao.StoreDao;
import com.logindemo.domain.Order;
import com.logindemo.service.StoreService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;

/**
 * Created by marnon on 2018/5/22.
 */
@Service
public class StoreServiceImpl implements StoreService{

    private static final String COMMONHEAD = "REPORT";

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private HistoryDao historyDao;

    @Override
    public String getStoreIndex(int employer, Date today) {
        Jedis redis = RedisUtil.getJedis();
        String date = DateUtil.getStringFromDate(today);

        //获取redis中某用户某日的数据
        String json = redis.get(COMMONHEAD + "14000001" + employer + date);

        //redis中没有需要的数据
        if(json == null || json.equals("")){
            List<Order> orders = historyDao.getReportOrder(date);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", orders);
            jsonObject.put("hotGoods", storeDao.getHotGoodsNums(employer, date));
            jsonObject.put("saleGoods", storeDao.getHotGoodsNums(employer, date));
            json = jsonObject.toString();
            //设置redis数据
            redis.set(COMMONHEAD + "14000001" + employer + date, json);
        }

        return json;
    }

    @Override
    public void getHotGoods() {

    }

    @Override
    public void getActivity() {

    }
}
