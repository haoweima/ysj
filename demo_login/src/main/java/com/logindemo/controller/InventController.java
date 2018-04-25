package com.logindemo.controller;

import com.logindemo.domain.Goods;
import com.logindemo.domain.GoodsType;
import com.logindemo.domain.ListComponent;
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
import java.util.List;

/**
 * Created by marnon on 2017/8/14.
 */

@Controller
@RequestMapping("/invent")
public class InventController {

    @Autowired
    private HistoryService historyService;

    /**
     * 库存管理页
     * @return
     */
    @RequestMapping("/inventPage")
    public String toInventPage(){
        return "person-inventory";
    }

    /**
     * 库存查询页面
     * @return
     */
    @RequestMapping("/selectInvent")
    public String selectInvent(HttpSession session, Model model){
        List<String> inventories = historyService.getInvent((int)session.getAttribute("employer"));

        model.addAttribute("invent",inventories);

        return "select-inventory";
    }

    /**
     * 获取指定仓库库存
     * @param ssc
     * @return
     */
    @RequestMapping("/getInventDetail")
    public void getInventDetail(HttpServletResponse response, HttpSession session, String ssc){
        JSONObject result = new JSONObject();

        result.put("invTable", historyService.getInvenTable((int)session.getAttribute("employer"),ssc));
        response.setCharacterEncoding("UTF-8");

        try {
            PrintWriter out = response.getWriter();
            out.append(result.toString());
            out.close();
        }catch (Exception e){

        }

    }

    /**
     * 查看商品
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/selectGoods")
    public String selectGoods(HttpSession session,  Model model){
        List<Goods> goods = historyService.getGoods((int)session.getAttribute("employer"));

        model.addAttribute("goods", goods);

        return "select-goods";
    }

    /**
     * 添加商品类型页面
     * @return
     */
    @RequestMapping("/addGoodsPage")
    public String addGoodsPage(){
        return "add-goods";
    }

    @RequestMapping("/addGoodsType")
    public String addGoodsType(ListComponent goods, HttpServletRequest request){
        List<GoodsType> list = goods.getGoods();
        HttpSession session = request.getSession();

        if(session.getAttribute("employer") != session.getAttribute("employee")){
            return "person-quick";
        }

        historyService.addGoodsType(list, (int)session.getAttribute("employer"));
        return "redirect:selectGoods";
    }

    @RequestMapping("/addVentPage")
    public String addVentPage(){
        return "add-vent";
    }

    @RequestMapping("/addVent")
    public String addVent(HttpServletRequest request){
        int employer = (int)request.getSession().getAttribute("employer");
        int employee = (int)request.getSession().getAttribute("employee");
        if(employee == employer) {
            historyService.addInvent(employer, request.getParameter("ssc"));
        }

        return "redirect:selectInvent";
    }

}
