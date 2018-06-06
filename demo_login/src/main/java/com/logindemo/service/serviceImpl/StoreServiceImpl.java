package com.logindemo.service.serviceImpl;

import com.logindemo.Utils.DateUtil;
import com.logindemo.Utils.RedisUtil;
import com.logindemo.dao.HistoryDao;
import com.logindemo.dao.StoreDao;
import com.logindemo.domain.ActivitySaleCouple;
import com.logindemo.domain.Goods;
import com.logindemo.domain.OrderDetail;
import com.logindemo.domain.ReportDomain;
import com.logindemo.service.StoreService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.*;


/**
 * Created by marnon on 2018/5/22.
 */
@Service
public class StoreServiceImpl implements StoreService{

    private static final String COMMONHEAD = "REPORT";

    private static final Jedis jedis = RedisUtil.getJedis();

    @Autowired
    private StoreDao storeDao;

    @Autowired
    private HistoryDao historyDao;

    //商铺近况
    @Override
    public ReportDomain getStoreIndex(int employer, String today) {

        ReportDomain result = new ReportDomain();

        String key = COMMONHEAD + "1400000" + employer + today;

        //缓存中存在需要的值
        if(jedis.get(key) != null){
            JSONObject jsonObject = JSONObject.fromObject(jedis.get(key));
            result.setSelectDate(today.substring(4));
            result.setSaleMoney(jsonObject.get("saleMoney").toString());
            result.setComeMoney(jsonObject.get("comeMoney").toString());
            result.setReturnMoney(jsonObject.get("returnMoney").toString());
            result.setChangeMoney(jsonObject.get("changeMoney").toString());
            String str = jsonObject.get("hotGoods").toString();
            List<String> list = Arrays.asList(str.substring(1, str.length() - 1).split(","));
            List<Integer> hot = new ArrayList<>();
            for(String s : list){
                if(!s.equals("")){
                    hot.add(Integer.parseInt(s));
                }
            }
            result.setHotGoods(hot);

            str = jsonObject.get("profitGoods").toString();
            list = Arrays.asList(str.substring(1, str.length() - 1).split(","));
            hot = new ArrayList<>();

            for(String s : list){
                if(!s.equals("")){
                    hot.add(Integer.parseInt(s));
                }
            }
            result.setProfitGoods(hot);

            return result;
        }

        result.setSaleMoney(storeDao.getSumOfDay(employer, DateUtil.typeFormat(today, DateUtil.YYYYMMDD, DateUtil.YYYY_MM_DD), "sale") + "");
        result.setComeMoney(storeDao.getSumOfDay(employer, DateUtil.typeFormat(today, DateUtil.YYYYMMDD, DateUtil.YYYY_MM_DD), "come") + "");
        result.setReturnMoney(storeDao.getSumOfDay(employer, DateUtil.typeFormat(today, DateUtil.YYYYMMDD, DateUtil.YYYY_MM_DD), "return") + "");
        result.setChangeMoney(storeDao.getSumOfDay(employer, DateUtil.typeFormat(today, DateUtil.YYYYMMDD, DateUtil.YYYY_MM_DD), "change") + "");
        result.setHotGoods(storeDao.getHotGoodsNums(employer, DateUtil.typeFormat(today, DateUtil.YYYYMMDD, DateUtil.YYYY_MM_DD)));
        result.setProfitGoods(storeDao.getHotGoodsSale(employer, DateUtil.typeFormat(today, DateUtil.YYYYMMDD, DateUtil.YYYY_MM_DD)));
        result.setSelectDate(today.substring(4));

        //添加进redis
        JSONObject addJSON = JSONObject.fromObject(result);
        jedis.set(key, addJSON.toString());

        return result;
    }

    //热销商品
    @Override
    public List<Goods> getHotGoods(int employer, List<ReportDomain> var, String dateS, String dateE) {
        Set<Integer> hotGoods = new HashSet<>();

        for(ReportDomain dto : var){
            hotGoods.addAll(dto.getHotGoods());
        }

        String ids = getIdsFromList(hotGoods);
        if(ids.equals("") || ids == null){
            return null;
        }

        List<OrderDetail> details = storeDao.getDetailByIds(ids, dateS, dateE);

        List<Goods> goods = storeDao.getHotGoodsById(goodsTree(details));

        return goods;
    }

    //活动推荐
    @Override
    public List<ActivitySaleCouple> getActivity(int employer, List<Goods> hotGoods) {
        List<ActivitySaleCouple> result = new ArrayList<>();

        List<Goods> coldGoods = storeDao.getLowSaleGoodsByUserId(employer);

        for(Goods hot : hotGoods){
            for(Goods cold : coldGoods){
                ActivitySaleCouple temp = new ActivitySaleCouple();
                temp.setNameFirst(hot.getName());
                temp.setSaleOld(hot.getSale_price());
                temp.setNameSecond(cold.getName());
                temp.setSaleNew(cold.getSale_price());
                temp.setSaleMoney((hot.getSale_price() + cold.getSale_price()) * 0.8);
                temp.setProfit(temp.getSaleMoney() - hot.getCome_price() - cold.getCome_price());
                result.add(temp);
            }
        }

        return result;
    }

    private String getIdsFromList(Set<Integer> var){
        Iterator it = var.iterator();
        StringBuilder ids = new StringBuilder();

        while(it.hasNext()){
            ids.append(it.next());
            if(it.hasNext())
                ids.append(",");
        }

        return ids.toString();
    }

    //决策树
    private String goodsTree(List<OrderDetail> var){
        double numAvg = 0, moneyAvg = 0;

        for(OrderDetail det : var){
            numAvg += det.getNum();
            moneyAvg += det.getMoney();
        }

        numAvg = numAvg/var.size();
        moneyAvg = moneyAvg/var.size();

        int[][] arr = new int[var.size()][3];

        int flag1 = 0, flag2 = 0;
        for(int i = 0; i < var.size(); i++){
            arr[i][0] = var.get(i).getIdGoods();

            if(var.get(i).getNum() > numAvg) {
                arr[i][1] = 1;
                flag1++;
            }
            else arr[i][1] = 0;

            if(var.get(i).getMoney() > moneyAvg){
                arr[i][2] = 1;
                flag2++;
            }
            else arr[i][2] = 0;
        }

        numAvg = (0-flag1/arr.length) * Math.log(0-flag1/arr.length) + (flag1/arr.length - 1) * Math.log(flag1/arr.length - 1);
        moneyAvg = (0-flag2/arr.length) * Math.log(0-flag2/arr.length) + (flag2/arr.length - 1) * Math.log(flag2/arr.length - 1);

        Set<Integer> hotGoods = new HashSet<>();

        int f,s;
        if(numAvg > moneyAvg){
            f = 1;
            s = 2;
        }else{
            f = 2;
            s = 1;
        }

        for(int i = 0; i < arr.length; i++){
            if(arr[i][f] == 1){
                hotGoods.add(arr[i][0]);
            }else if(arr[i][f] == 0){
                arr[i][s] = 0;
            }
        }

        for(int i = 0; i < arr.length; i++){
            if(arr[i][s] == 1){
                hotGoods.add(arr[i][0]);
            }
        }

        return getIdsFromList(hotGoods);
    }
}
