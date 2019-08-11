package com.lpp.life.community.controller;

import com.lpp.life.community.mapper.UserMapper;
import com.lpp.life.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/")
    public String test(Model model, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie:cookies) {
                if(cookie.getName().equals("token")){
                    User byToken = userMapper.findByToken(cookie.getValue());
                    if(byToken!=null){
                        request.getSession().setAttribute("user",byToken);
                    }
                }
            }
        }

        return "index";
    }
}
