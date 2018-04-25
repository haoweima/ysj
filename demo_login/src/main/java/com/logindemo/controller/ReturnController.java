package com.logindemo.controller;

import com.logindemo.domain.ListComponent;
import com.logindemo.domain.Order;
import com.logindemo.domain.Table;
import com.logindemo.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by marnon on 2017/8/14.
 */

@Controller
@RequestMapping("/return")
public class ReturnController {

    @Autowired
    private HistoryService historyService;

    private ToolsController tools = new ToolsController();
    /**
     * 退换货一级页面
     * @return
     */
    @RequestMapping("/returnPage")
    public String toReturnPage(){
        return "person-return";
    }

    /**
     * 新增退货页面
     * @return
     */
    @RequestMapping("/addReturnPage")
    public String addReturnPage(HttpSession session, Model model){
        List<String> inventories = historyService.getInvent((int)session.getAttribute("employer"));

        model.addAttribute("invent",inventories);
        return "add-return";
    }

    /**
     * 新增换货页面
     * @return
     */
    @RequestMapping("/addChangePage")
    public String addChangePage(HttpSession session, Model model){
        List<String> inventories = historyService.getInvent((int)session.getAttribute("employer"));

        model.addAttribute("invent",inventories);
        return "add-change";
    }

    /**
     * 退货历史详情页面
     * @return
     */
    @RequestMapping("/historyReturnPage")
    public String historyReturnPage(String id, Model model){
        historyService.getDetailsById(id, model);

        return "history-return";
    }

    /**
     * 换货历史详情页面
     * @return
     */
    @RequestMapping("/historyChangePage")
    public String historyChangePage(String id, Model model){
        historyService.getDetailsById(id, model);

        return "history-change";
    }

    /**
     * 退货订单预览页面
     * @return
     */
    @RequestMapping("/selectReturnPage")
    public String selectReturnPage(int employer, Model model, HttpSession session){
        List<Order> result = historyService.getOrder("return", (int)session.getAttribute("employer"), null);

        model.addAttribute("orders", result);
        return "select-return";
    }

    /**
     * 换货订单预览页面
     * @return
     */
    @RequestMapping("/selectChangePage")
    public String selectChangePage(int employer, Model model, HttpSession session){
        List<Order> result = historyService.getOrder("change", (int)session.getAttribute("employer"), null);

        model.addAttribute("orders", result);
        return "select-change";
    }

    /**
     * 新增退货
     * @param table
     * @param request
     * @return
     */
    @RequestMapping("/addReturn")
    public String addReturn(ListComponent table, HttpServletRequest request){
        List<Table> tables = table.getTable();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = df.format(date);
        Map<String, Object> map = tools.getUser(request.getSession());

        if(historyService.addOrder(tables, "return", d, map, request.getParameter("ssc"))){
            return "redirect:selectReturnPage?employer=" + map.get("employer") + "&tip=" + 1;
        }else return "redirect:selectReturnPage?employer=" + map.get("employer") + "&tip=" + 0;


//        return "redirect:addChangePage";
    }

    /**
     * 新增换货
     * @param table
     * @param request
     * @return
     */
    @RequestMapping("/addChange")
    public String addChange(ListComponent table, HttpServletRequest request){
        List<Table> tables = table.getTable();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = df.format(date);
        Map<String, Object> map = tools.getUser(request.getSession());

        if(historyService.addOrder(tables, "change", d, map, request.getParameter("ssc"))){
            return "redirect:selectChangePage?employer=" + map.get("employer") + "&tip=" + 1;
        }else return "redirect:selectChangePage?employer=" + map.get("employer") + "&tip=" + 0;


    }

    @RequestMapping("/deleteOrder")
    public String deleteOrder(String id, HttpSession session, String type){
        historyService.deleteOrder(id);

        if(type.equals("return")) {
            return "redirect:selectReturnPage?employer=" + session.getAttribute("employer");
        }else{
            return "redirect:selectChangePage?employer=" + session.getAttribute("employer");
        }
    }
}
