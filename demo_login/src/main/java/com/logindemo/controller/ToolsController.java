package com.logindemo.controller;

import com.logindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marnon on 2017/8/14.
 */

public class ToolsController {
    @Autowired
    private UserService userService;

    public Map<String, Object> getUser(HttpSession session){
        Map<String, Object> map = new HashMap<>();

        map.put("employer", session.getAttribute("employer"));
        map.put("employee", session.getAttribute("employee"));

        return map;
    }

//    public void modelAddUser(int id, ModelAndView model){
//        model.addObject("user",userService.getUserById(id));
//    }
}
