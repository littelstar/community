package com.lpp.life.community.controller;

import com.lpp.life.community.dto.QuestionDto;
import com.lpp.life.community.mapper.UserMapper;
import com.lpp.life.community.model.User;
import com.lpp.life.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

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

        ArrayList<QuestionDto> questionDtos = questionService.getQuestionDto();
        model.addAttribute("questions",questionDtos);
        return "index";
    }
}
