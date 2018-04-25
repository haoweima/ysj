package com.logindemo.other;

import com.logindemo.controller.LoginController;
import com.logindemo.domain.User;
import com.logindemo.encrypt.MD5;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by marnon on 2017/7/6.
 */

@Service
public class MyInterceptor implements HandlerInterceptor {
    private LoginController loginController = new LoginController();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie[] cookies = httpServletRequest.getCookies();

        String account = null;                        // 账号
        String ssid = null;                           // ssid
        User user = new User();

        if(cookies != null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("account")){
                    account = cookie.getValue();
                }
                if(cookie.getName().equals("pass")){
                    ssid = cookie.getValue();
                }
            }
        }

        if(account != null && ssid != null){
            String md5 = MD5.getMD5( MD5.getKey() + account );
            if(ssid.equals(md5)){
                return true;
            }
        }

        httpServletResponse.sendRedirect("login");

//        System.out.println("has been interceped");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//        tools.modelAddUser((int)httpServletRequest.getSession().getAttribute("employee"), modelAndView);
        HttpSession session = httpServletRequest.getSession();
        try{
            modelAndView.addObject("name", session.getAttribute("name"));
            if(session.getAttribute("employer") == session.getAttribute("employee")){
                modelAndView.addObject("power", 1);
            }
            else{
                modelAndView.addObject("power", 0);
            }
        }catch (Exception e){

        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
