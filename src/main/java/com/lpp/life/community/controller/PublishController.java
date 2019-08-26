package com.lpp.life.community.controller;

import com.lpp.life.community.cache.TagCache;
import com.lpp.life.community.dto.TagDto;
import com.lpp.life.community.model.Question;
import com.lpp.life.community.model.User;
import com.lpp.life.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish")
    public String publish(Model model){
        List<TagDto> tagDtos = TagCache.get();
        model.addAttribute("tagCategory",tagDtos);
        return "publish";

    }

    @PostMapping("/publish")
    public String insertQuestion(@RequestParam(value = "title", required=false) String title,
                                 @RequestParam(value = "description",required=false) String description,
                                 @RequestParam(value = "tags" , required=false) String tags,
                                 @RequestParam(value = "id",required = false) Long id,
                                 HttpServletRequest request,Model model){
        String s = TagCache.filterInValid(tags);
        if(StringUtils.isBlank(s)){
            model.addAttribute("error",s+"标签不存在");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTags(tags);
        question.setId(id);
        User user = (User) request.getSession().getAttribute("user");
        question.setCreator(user.getId());
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
