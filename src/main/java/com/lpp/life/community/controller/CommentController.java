package com.lpp.life.community.controller;

import com.lpp.life.community.dto.CommentDto;
import com.lpp.life.community.dto.ResultDto;
import com.lpp.life.community.exception.CustomizeErrorCode;
import com.lpp.life.community.mapper.CommentMapper;
import com.lpp.life.community.model.Comment;
import com.lpp.life.community.model.User;
import com.lpp.life.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method =RequestMethod.POST)
    public Object post(@RequestBody CommentDto commentDto, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDto.errorOf(CustomizeErrorCode.USER_NOT_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDto.getParentId());
        comment.setContent(commentDto.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setLikeCount(0l);
        comment.setType(commentDto.getType());
        comment.setCommentatorId(user.getId());
        commentService.insert(comment);

        return ResultDto.successOf(CustomizeErrorCode.SUCCESS_COMMNET);
    }
}
