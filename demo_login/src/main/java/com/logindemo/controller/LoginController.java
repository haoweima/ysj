package com.logindemo.controller;

import com.logindemo.domain.User;
import com.logindemo.encrypt.MD5;
import com.logindemo.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marnon on 2017/7/23.
 */

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String welcome(){
        return "welcome";
    }

    /**
     * 进入到登陆页面
     */
    @RequestMapping("/login")
    public String getLogin(HttpServletRequest request, Model model){
        if(request.getParameter("tip") != null){
            if(request.getParameter("tip").equals("1")){
                model.addAttribute("tip", "用户名或密码错误！");
            }
        }
        return "login";
    }

    /**
     * 进入到注册页面
     * @return
     */
    @RequestMapping("/register")
    public String getRegister(){
        return "register";
    }

    /**
     * 判断用户名是否重复
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("/check")
    public void checkUser(HttpServletRequest request, HttpServletResponse response, Model model){
        if(request.getParameter("account") != null){
            Boolean temp = userService.checkAccount(request.getParameter("account"));
            if(temp == true){//用户名存在
                try {
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().print("用户名重复");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 登陆操作
     * @return
     */
    @RequestMapping("/signin")
    public String getSignin(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> map = new HashMap<>();
        map.put("account", request.getParameter("account"));
        map.put("pass", request.getParameter("pass"));
        User user = userService.getUserMessage(map.get("account"));
        HttpSession session = request.getSession();

        if(userService.signIn(map.get("account"),map.get("pass"))) {
            Cookie account = new Cookie("account", map.get("account"));
            Cookie pass = new Cookie("pass", MD5.getMD5(MD5.getKey() + map.get("account")));
            session.setAttribute("employer", user.getEmployer());
            session.setAttribute("employee", user.getIdUser());
            session.setAttribute("name", user.getName());

            session.setMaxInactiveInterval(3600 * 8);
            account.setMaxAge(3600 * 8);
            pass.setMaxAge(3600 * 8);

            response.addCookie(account);
            response.addCookie(pass);

            return "redirect:person";
        }
        else return "redirect:login?tip=1";
    }

    /**
     * 注册操作
     * @return
     */
    @RequestMapping("/signup")
    public String getSignup(HttpServletRequest request, HttpServletResponse response){
        Map<String, String> map = new HashMap<>();
        map.put("account", request.getParameter("account"));
        map.put("pass",request.getParameter("pass1"));
        map.put("name",request.getParameter("name"));

        Boolean temp = userService.signUp(map.get("account"), map.get("pass"), map.get("name"));

        if(temp == true){
//            Cookie account = new Cookie("account", map.get("account"));
//            Cookie pass = new Cookie("pass", MD5.getMD5(MD5.getKey() + map.get("account")));
//
//            account.setMaxAge(3600 * 24);
//            pass.setMaxAge(3600 * 24);
//
//            response.addCookie(account);
//            response.addCookie(pass);
//
//            return "redirect:person";
            return "buyit";
        }else
            return "redirect:register";
    }

    /**
     * 注销操作
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/signout")
    public String getSignout(HttpServletRequest request, HttpServletResponse response){
        Cookie account = new Cookie("account", null);
        Cookie pass = new Cookie("pass", null);

        account.setMaxAge(0);
        pass.setMaxAge(0);

        response.addCookie(account);
        response.addCookie(pass);

        return "redirect:/";
    }

    /**
     * 进入个人主页
     * @return
     */
    @RequestMapping("/person")
    public String getPerson(){
        return "person-quick";
    }

//    @RequestMapping("/userMessage")
//    public User getUserMessage(javax.servlet.http.HttpServletRequest request){
//        User user = userService.getUserById((int)request.getSession().getAttribute("employee"));
//
//        return user;
//    }
}
