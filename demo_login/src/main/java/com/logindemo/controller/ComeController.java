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
@RequestMapping("/come")
public class ComeController {

    @Autowired
    private HistoryService historyService;

    private ToolsController tools = new ToolsController();

    @RequestMapping("/comePage")
    public String toComePage(){
        return "person-come";
    }

    @RequestMapping("/addPage")
    public String addPage(HttpSession session, Model model){
        List<String> inventories = historyService.getInvent((int)session.getAttribute("employer"));
        Map<String, List> map = historyService.getAllGoodsNames((int)session.getAttribute("employer"));
        model.addAttribute("invent",inventories);
        model.addAttribute("gNames",map.get("names"));

        return "add-come";
    }

    /**
     * 新增进货
     * @param table
     * @param request
     * @return
     */
    @RequestMapping("/addCome")
    public String addSale(ListComponent table, HttpServletRequest request){
        List<Table> tables = table.getTable();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = df.format(date);
        Map<String, Object> map = tools.getUser(request.getSession());

        if(historyService.addOrder(tables, "come", d, map, request.getParameter("ssc"))){
            return "redirect:selectPage?employer=" + map.get("employer") + "&tip=" + 1;
        }else return "redirect:selectPage?employer=" + map.get("employer") + "&tip=" + 0;


    }

    @RequestMapping("/selectPage")
    public String selectPage(int employer, Model model, HttpSession session){
        List<Order> result = historyService.getOrder("come", (int)session.getAttribute("employer"), null);

        model.addAttribute("orders", result);
        return "select-come";
    }

    /**
     * 详细订单
     * @return
     */
    @RequestMapping("/historyPage")
    public String historyPage(String id, Model model){
        historyService.getDetailsById(id, model);

        return "history-come";
    }

    @RequestMapping("/deleteOrder")
    public String deleteOrder(String id, HttpSession session){
        historyService.deleteOrder(id);

        return "redirect:selectPage?employer=" + session.getAttribute("employer");
    }

    @RequestMapping("/getGoodsMess")
    public void getGoodsMess(String name, HttpServletResponse response){
        JSONObject result = new JSONObject();

        try {
            result.put("gMess", historyService.getGoodsUnit(name).get(0));
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
}
