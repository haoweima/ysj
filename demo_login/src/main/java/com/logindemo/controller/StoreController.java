package com.logindemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by marnon on 2018/5/21.
 */
@Controller
@RequestMapping("/store")
public class StoreController {

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
    public void getStoreIndex(HttpServletRequest request, HttpServletResponse response){

    }

    //热门商品
    @RequestMapping("/hotGoods")
    public String hotGoodsUI(){
        return "hotGoods";
    }

    @ResponseBody
    @RequestMapping("/getHotGoods")
    public void getHotGoods(HttpServletRequest request, HttpServletResponse response){

    }

    //活动智能规划
    @RequestMapping("/activity")
    public String activityUI(){
        return "activity";
    }

    @ResponseBody
    @RequestMapping("/getActivity")
    public void getActivity(HttpServletRequest request, HttpServletResponse response){

    }

}
