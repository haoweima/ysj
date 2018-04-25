package com.logindemo.controller;

import com.logindemo.domain.ListComponent;
import com.logindemo.domain.Order;
import com.logindemo.domain.Table;
import com.logindemo.service.HistoryService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by marnon on 2017/8/14.
 */

@Controller
@RequestMapping("/sale")
public class SaleController {
    @Autowired
    private HistoryService historyService;

    private ToolsController tools = new ToolsController();

    @RequestMapping("/salePage")
    public String toSalePage(){
        return "person-sale";
    }

    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        List<String> inventories = historyService.getInvent((int)session.getAttribute("employer"));

        model.addAttribute("invent",inventories);

        return "add-sale";
    }

    /**
     * 选择仓库后为前端datalist添加数据
     * @param request
     * @param ssc
     * @return
     */
    @RequestMapping("/getNames")
    public void getGoodsNames(HttpServletRequest request, HttpServletResponse response, String ssc, Model model){
        HttpSession session = request.getSession();
        List<String> gNames = historyService.getGoodsNames((int)session.getAttribute("employer"), ssc);
        JSONObject result = new JSONObject();
        result.put("gNames", gNames);

        try {
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.append(result.toString());
            out.close();
        }catch (Exception e) {
            System.out.println(e);
        }


//        model.addAttribute("gNames", gNames);
    }

    /**
     * 新增销售
     * @param table
     * @param request
     * @return
     */
    @RequestMapping("/addSale")
    public String addSale(ListComponent table, HttpServletRequest request){
        List<Table> tables = table.getTable();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = df.format(date);
        Map<String, Object> map = tools.getUser(request.getSession());

        if(historyService.addOrder(tables, "sale", d, map, request.getParameter("ssc"))){
            return "redirect:selectPage?employer=" + map.get("employer") + "&tip=" + 1;
        }else
            return "redirect:selectPage?employer=" + map.get("employer") + "&tip=" + 0;
    }

    /**
     * 查看订单详情
     * @param id
     * @return
     */
    @RequestMapping("/historyPage")
    public String historyPage(String id, Model model){
        historyService.getDetailsById(id, model);

        return "history-sale";
    }

    /**
     * 查看订单总览
     * @param employer
     * @param model
     * @return
     */
    @RequestMapping("/selectPage")
    public String selectPage(int employer, Model model, HttpSession session){
        List<Order> result = historyService.getOrder("sale", (int)session.getAttribute("employer"), null);

        model.addAttribute("orders", result);
        return "select-sale";
    }

    /**
     * 获得所选商品信息
     * @param name
     * @param response
     */
    @RequestMapping("/getGoodsMess")
    public void getGoodsMess(String name, HttpServletResponse response){
        JSONObject result = new JSONObject();

        try {
            result.put("gMess", historyService.getGoods(name).get(0));
        }catch (Exception e){

        }

        response.setCharacterEncoding("UTF-8");

        try{
            PrintWriter out = response.getWriter();
            out.append(result.toString());
            out.close();
        }catch (Exception e){

        }
    }

    @RequestMapping("/deleteOrder")
    public String deleteOrder(String id, HttpSession session){
        historyService.deleteOrder(id);

        return "redirect:selectPage?employer=" + session.getAttribute("employer");
    }
}
