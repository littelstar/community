package com.lpp.life.community.controller;

import com.lpp.life.community.dto.CommentCreateDto;
import com.lpp.life.community.dto.CommentDto;
import com.lpp.life.community.dto.ResultDto;
import com.lpp.life.community.enums.CommentTypeEnum;
import com.lpp.life.community.exception.CustomizeErrorCode;
import com.lpp.life.community.model.Comment;
import com.lpp.life.community.model.User;
import com.lpp.life.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method =RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDto.errorOf(CustomizeErrorCode.USER_NOT_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setLikeCount(0l);
        comment.setType(commentCreateDto.getType());
        comment.setCommentatorId(user.getId());
        comment.setCommentCount(0);
        commentService.insert(comment);
        return ResultDto.successOf(CustomizeErrorCode.SUCCESS_COMMNET);
    }

    @ResponseBody
    @RequestMapping("/getComment/{id}")
    public ResultDto<List> getCommentByComment(@PathVariable(value = "id") Long id){
        List<CommentDto> commentDtos = commentService.listByQuestionId(id,CommentTypeEnum.COMMENT);

        return ResultDto.successOf(commentDtos);
    }
}
