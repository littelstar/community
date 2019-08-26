package com.lpp.life.community.service;

import com.lpp.life.community.dto.CommentDto;
import com.lpp.life.community.enums.CommentTypeEnum;
import com.lpp.life.community.enums.NotificationEnum;
import com.lpp.life.community.exception.CustomizeErrorCode;
import com.lpp.life.community.exception.CustomizeException;
import com.lpp.life.community.mapper.*;
import com.lpp.life.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment,User commentator) {
        if (comment.getParentId() == null) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_PARENT_NOT_FOUNT);
        }
        Question question=null;
        if (comment.getType() == null) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        } else if (comment.getType() == CommentTypeEnum.QUESTION.getType()) {
//            问题的评论
             question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
            }
            question.setCommentCount(1);
            questionExtMapper.incComment(question);
//            添加通知
            addNotification(comment, question.getCreator(),commentator.getLogin(),question.getTitle(),NotificationEnum.REPLY_QUESTION.getType(),question.getId());

        }else if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
//            评论的回复
            Comment parentComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(parentComment == null){
                throw  new CustomizeException(CustomizeErrorCode.COMMENT_PARENT_NOT_FOUNT);
            }
            parentComment.setCommentCount(1);
//            添加通知
            addNotification(comment,parentComment.getCommentatorId(),commentator.getLogin(),parentComment.getContent(),NotificationEnum.REPLY_COMMENT.getType(),parentComment.getParentId());
            commentExtMapper.incCount(parentComment);
        }
        commentMapper.insert(comment);
    }

    /**
     *添加通知
     * @param comment：评论
     * @param creator:评论人的ID
     * @param notifierTitle:评论的人
     * @param outerTitle：被评论的评论或者问题
     */
    private void addNotification(Comment comment, Long creator,String notifierTitle,String outerTitle,Integer type,Long outId) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotifier(comment.getCommentatorId());
        notification.setOuterId(outId);
        notification.setReceiver(creator);
        notification.setStatus(0);
        notification.setType(type);
        notification.setNotifierTitle(notifierTitle);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);

    }

    public List<CommentDto> listByQuestionId(Long id,CommentTypeEnum commentTypeEnum) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(commentTypeEnum.getType());
        commentExample.setOrderByClause("gmt_modified desc");
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
