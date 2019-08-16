package com.lpp.life.community.controller;

import com.lpp.life.community.dto.PaginationDto;
import com.lpp.life.community.dto.QuestionDto;
import com.lpp.life.community.mapper.QuestionMapper;
import com.lpp.life.community.model.Question;
import com.lpp.life.community.model.QuestionExample;
import com.lpp.life.community.model.User;
import com.lpp.life.community.model.UserExample;
import com.lpp.life.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/profile/{action}")
    private String profile(@PathVariable(value = "action") String action, Model model,
                           @RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                           @RequestParam(value = "size",defaultValue = "5",required = false) Integer size,
                           HttpServletRequest request){
        if(action.equals("question")){
            model.addAttribute("sectionName","我的问题");
            model.addAttribute("section","question");
        }else if(action.equals("relies")){
            model.addAttribute("sectionName","最新回复");
            model.addAttribute("section","relies");
        }
        User byToken= (User) request.getSession().getAttribute("user");

        ArrayList<QuestionDto> questionDto = questionService.getQuestionDtoByUserId(byToken.getId(), page, size);
        PaginationDto paginationDto = new PaginationDto();
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(byToken.getId());
        Integer totalCount  = (int)questionMapper.countByExample(questionExample);
        paginationDto.setPagination(totalCount,page,size);
        paginationDto.setQuestionDtos(questionDto);
        model.addAttribute("profilepaginationDto",paginationDto);
        return "profile";
    }
}