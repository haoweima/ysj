package com.logindemo.job;

import com.logindemo.Utils.DateUtil;
import com.logindemo.dao.HistoryDao;
import com.logindemo.domain.Order;
import com.logindemo.domain.ReportDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 将数据查出来打包进redis
 * Created by marnon on 2018/5/17.
 */

@Component
public class DateToRedis {

    @Autowired
    private HistoryDao historyDao;

    @Scheduled(cron = "0 0 8 * * *")
    public void doScanner(){
        List<ReportDomain> result = new ArrayList<>();
        Map<Integer, Integer> employerId = new HashMap<>();

        Date date = new Date();
        String yesterday = DateUtil.getOffsetDateStr(date, 1);
        List<Order> orders = historyDao.getReportOrder(yesterday);

        //填充每天的销售情况
        for(int i = 0; i < orders.size(); i++){
            int employer = orders.get(i).getEmployer();
            String type = orders.get(i).getType();
            double income = orders.get(i).getIncome();

            if(employerId.get(employer) == null){
                //没有遍历过该用户
                int index = result.size();

                ReportDomain temp = fillReportDomain(type, income, null);

                if(temp != null) {
                    result.add(temp);
                    employerId.put(employer, index);
                }
            }else{
                int index = employerId.get(employer);
                fillReportDomain(type, income, result.get(index));
            }
        }

        //填充热门商品

    }

    private ReportDomain fillReportDomain(String type, double income, ReportDomain temp){
        if(temp == null) {
            temp = new ReportDomain();
        }

        if(type.equals("sale")){
            temp.setSaleMoney(income + "");
        }else if(type.equals("come")){
            temp.setComeMoney(income + "");
        }else if(type.equals("return")){
            temp.setReturnMoney(income + "");
        }else if(type.equals("change")){
            temp.setChangeMoney(income + "");
        }else{
            temp = null;
        }

        return temp;
    }
}
