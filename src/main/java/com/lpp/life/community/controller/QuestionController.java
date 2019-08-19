package com.lpp.life.community.controller;

import com.lpp.life.community.dto.QuestionDto;
import com.lpp.life.community.model.User;
import com.lpp.life.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    private String question(@PathVariable(value = "id") Integer id, Model model, HttpServletRequest request){
        QuestionDto questionById = questionService.getQuestionById(id);
        User user = (User)request.getSession().getAttribute("user");
        questionById.setUser(user);
        model.addAttribute("question",questionById);
        return "question";
    }

    @GetMapping("/questionEdit/{id}")
    private String questionEdit(@PathVariable(value = "id" ) Integer id,Model model){
        QuestionDto questionById = questionService.getQuestionById(id);
        model.addAttribute("id",questionById.getId());
        model.addAttribute("title",questionById.getTitle());
        model.addAttribute("description",questionById.getDescription());
        model.addAttribute("tags",questionById.getTags());
        return "publish";
    }
}
