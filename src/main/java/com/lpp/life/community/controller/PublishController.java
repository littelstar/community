package com.lpp.life.community.controller;

import com.lpp.life.community.mapper.QuestionMapper;
import com.lpp.life.community.model.Question;
import com.lpp.life.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";

    }

    @PostMapping("/publish")
    public String insertQuestion(@RequestParam(value = "title", required=false) String title,
                                 @RequestParam(value = "description",required=false) String description,
                                 @RequestParam(value = "tags" , required=false) String tags,
                                 HttpServletRequest request){
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTags(tags);
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        User user = (User) request.getSession().getAttribute("user");
        question.setCreator(user.getId());
        questionMapper.insertQuestion(question);
        return "redirect:/";
    }
}
