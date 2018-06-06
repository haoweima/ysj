package com.logindemo.controller;

import com.logindemo.Utils.DateUtil;
import com.logindemo.domain.ActivitySaleCouple;
import com.logindemo.domain.Goods;
import com.logindemo.domain.ReportDomain;
import com.logindemo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by marnon on 2018/5/21.
 */
@Controller
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @RequestMapping("/storePage")
    public String toStorePage(){
        return "person-store";
    }

    //商铺近况
    @RequestMapping("/storeIndex")
    public String storeIndexUI(){
        return "storeIndex";
    }

    @ResponseBody
    @RequestMapping("/getStoreIndex")
    public List<ReportDomain> getStoreIndex(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        int employer = (int)session.getAttribute("employer");
        List<ReportDomain> result = new ArrayList<>();
        Date today = new Date();
        Date yesterday = DateUtil.getDateFromString(DateUtil.getOffsetDateStr(today,1,DateUtil.YYYYMMDD), DateUtil.YYYYMMDD);
        List<String> date = DateUtil.getNearDate(yesterday, 30);

        for(String s : date){
            result.add(storeService.getStoreIndex(employer, s));
        }

        return result;
    }

    //热门商品
    @RequestMapping("/hotGoods")
    public String hotGoodsUI(){
        return "hotGoods";
    }

    @ResponseBody
    @RequestMapping("/getHotGoods")
    public List<Goods> getHotGoods(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        List<ReportDomain> reqDto = getStoreIndex(request, response);
        Date date = new Date();

        List<Goods> hotGoods = storeService.getHotGoods((int)session.getAttribute("employer"), reqDto,
                DateUtil.getOffsetDateStr(date, 30, DateUtil.YYYYMMDD), DateUtil.getStringFromDate(date, DateUtil.YYYYMMDD));

        return hotGoods;
    }

    //活动智能规划
    @RequestMapping("/activity")
    public String activityUI(){
        return "activity";
    }

    @ResponseBody
    @RequestMapping("/getActivity")
    public List<ActivitySaleCouple> getActivity(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        List<Goods> hotGoods = getHotGoods(request, response);

        List<ActivitySaleCouple> activitySaleCouples = storeService.getActivity((int)session.getAttribute("employer"), hotGoods);

        return activitySaleCouples;
    }

}
