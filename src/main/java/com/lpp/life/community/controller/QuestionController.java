package com.lpp.life.community.controller;

import com.lpp.life.community.dto.CommentDto;
import com.lpp.life.community.dto.QuestionDto;
import com.lpp.life.community.enums.CommentTypeEnum;
import com.lpp.life.community.model.Question;
import com.lpp.life.community.service.CommentService;
import com.lpp.life.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;


    @GetMapping("/question/{id}")
    private String question(@PathVariable(value = "id") Long id, Model model, HttpServletRequest request){
        QuestionDto questionById = questionService.getQuestionById(id);
//        增加阅读数
        questionService.incView(id);
//        获取评论
        List<CommentDto> comments=commentService.listByQuestionId(questionById.getId(),CommentTypeEnum.QUESTION);
//        获取相关问题
        List<Question> questions = questionService.selectRelated(questionById);
        model.addAttribute("comments",comments);
        model.addAttribute("question",questionById);
        model.addAttribute("relatedQuestions",questions);
        return "question";
    }

    @GetMapping("/questionEdit/{id}")
    private String questionEdit(@PathVariable(value = "id" ) Long id,Model model){
        QuestionDto questionById = questionService.getQuestionById(id);
        model.addAttribute("id",questionById.getId());
        model.addAttribute("title",questionById.getTitle());
        model.addAttribute("description",questionById.getDescription());
        model.addAttribute("tags",questionById.getTags());
        return "publish";
    }
}
