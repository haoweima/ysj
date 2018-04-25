package com.logindemo.controller;

import com.logindemo.domain.Employee;
import com.logindemo.domain.ListComponent;
import com.logindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by marnon on 2017/8/14.
 */

@Controller
@RequestMapping("/employ")
public class EmployController {

    @Autowired
    private UserService userService;

    /**
     * 用户一级页面
     * @return
     */
    @RequestMapping("/employPage")
    public String toEmployPage(){
        return "person-employee";
    }

    /**
     * 添加员工页面
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(){
        return "add-employee";
    }

    @RequestMapping("/addEmp")
    public String addEmployer(ListComponent employee, HttpSession session){
        List<Employee> employeeList = employee.getEmployee();

        if(session.getAttribute("employer") == session.getAttribute("employee")) {
            userService.addEmployee(employeeList, (int) session.getAttribute("employer"));
        }

        return "redirect:selectPage";
    }

    /**
     * 查询员工页面
     * @return
     */
    @RequestMapping("/selectPage")
    public String selectPage(HttpSession session, Model model){
        if(session.getAttribute("employer") != session.getAttribute("employee")){
            return "redirect:/person";
        }

        model.addAttribute("emps", userService.getEmployee((int)session.getAttribute("employer")));

        return "select-employee";
    }
}
