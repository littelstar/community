package com.lpp.life.community.controller;

import com.lpp.life.community.dto.PaginationDto;
import com.lpp.life.community.dto.QuestionDto;
import com.lpp.life.community.mapper.QuestionMapper;
import com.lpp.life.community.mapper.UserMapper;
import com.lpp.life.community.model.QuestionExample;
import com.lpp.life.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;

    @RequestMapping("/")
    public String test(Model model, HttpServletRequest request,@RequestParam(value = "page",defaultValue = "1",required = false) Integer page,
                       @RequestParam(value = "size",defaultValue = "5",required = false) Integer size){
//        Cookie[] cookies = request.getCookies();
//        if(cookies!=null){
//            for (Cookie cookie:cookies) {
//                if(cookie.getName().equals("token")){
//                    User byToken = userMapper.findByToken(cookie.getValue());
//                    if(byToken!=null){
//                        request.getSession().setAttribute("user",byToken);
//                    }
//                }
//            }
//        }
//        User user = (User) request.getSession().getAttribute("user");
        ArrayList<QuestionDto> questionDto = questionService.getQuestionDto(page, size);
        PaginationDto paginationDto = new PaginationDto();

        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        paginationDto.setPagination(totalCount,page,size);
        paginationDto.setQuestionDtos(questionDto);
        model.addAttribute("paginationDto",paginationDto);
        return "index";
    }
}
