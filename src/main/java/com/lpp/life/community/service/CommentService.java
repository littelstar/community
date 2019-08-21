package com.lpp.life.community.service;

import com.lpp.life.community.dto.CommentDto;
import com.lpp.life.community.enums.CommentTypeEnum;
import com.lpp.life.community.exception.CustomizeErrorCode;
import com.lpp.life.community.exception.CustomizeException;
import com.lpp.life.community.mapper.CommentMapper;
import com.lpp.life.community.mapper.QuestionExtMapper;
import com.lpp.life.community.mapper.QuestionMapper;
import com.lpp.life.community.mapper.UserMapper;
import com.lpp.life.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    public void insert(Comment comment) {
        if (comment.getParentId() == null) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_PARENT_NOT_FOUNT);
        }
        Question question=null;
        if (comment.getType() == null) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        } else if (comment.getType() == CommentTypeEnum.QUESTION.getType()) {
             question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
            }
        }else if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(parentComment == null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_PARENT_NOT_FOUNT);
            }
        }
        question.setCommentCount(1);
        questionExtMapper.incComment(question);
        commentMapper.insert(comment);
    }

    public List<CommentDto> listByQuestionId(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size() == 0){
            return new ArrayList<>();
        }
            Set<Long> collect = comments.stream().map(comment -> comment.getCommentatorId()).collect(Collectors.toSet());
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdIn(new ArrayList<Long>(collect));
            List<User> users = userMapper.selectByExample(userExample);
            Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

            List<CommentDto> commentDtos = comments.stream().map(comment -> {
                CommentDto commentDto = new CommentDto();
                BeanUtils.copyProperties(comment,commentDto);
                commentDto.setUser(userMap.get(comment.getCommentatorId()));
                return commentDto;
            }).collect(Collectors.toList());

        return commentDtos;
    }
}
