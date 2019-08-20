package com.lpp.life.community.service;

import com.lpp.life.community.enums.CommentTypeEnum;
import com.lpp.life.community.exception.CustomizeErrorCode;
import com.lpp.life.community.exception.CustomizeException;
import com.lpp.life.community.mapper.CommentMapper;
import com.lpp.life.community.mapper.QuestionExtMapper;
import com.lpp.life.community.mapper.QuestionMapper;
import com.lpp.life.community.model.Comment;
import com.lpp.life.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

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
}
