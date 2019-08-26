package com.lpp.life.community.controller;

import com.lpp.life.community.dto.PaginationDto;
import com.lpp.life.community.dto.QuestionDto;
import com.lpp.life.community.mapper.QuestionMapper;
import com.lpp.life.community.model.QuestionExample;
import com.lpp.life.community.model.User;
import com.lpp.life.community.service.NotificationService;
import com.lpp.life.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;



    @GetMapping("/profile/{action}")
    private String profile(@PathVariable(value = "action") String action, Model model,
                           @RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                           @RequestParam(value = "size",defaultValue = "5",required = false) Integer size,
                           HttpServletRequest request){
        User byToken= (User) request.getSession().getAttribute("user");
        if(action.equals("question")){
//            问题展示页面
            model.addAttribute("sectionName","我的问题");
            model.addAttribute("section","question");
            PaginationDto paginationDto = questionService.getQuestionDtoByUserId(byToken.getId(), page, size);
            model.addAttribute("profilepaginationDto",paginationDto);
        }else if(action.equals("relies")){
//            回复页面
            PaginationDto paginationDto=notificationService.getNotification(byToken.getId(),page,size);
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("section","relies");
            model.addAttribute("profilepaginationDto",paginationDto);
        }


        return "profile";
    }
}
