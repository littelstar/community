package com.lpp.life.community.service;

import com.lpp.life.community.dto.NotificationDto;
import com.lpp.life.community.dto.PaginationDto;
import com.lpp.life.community.enums.NotificationEnum;
import com.lpp.life.community.enums.NotificationStatusEnum;
import com.lpp.life.community.exception.CustomizeErrorCode;
import com.lpp.life.community.exception.CustomizeException;
import com.lpp.life.community.mapper.NotificationMapper;
import com.lpp.life.community.mapper.UserMapper;
import com.lpp.life.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserMapper userMapper;
    /**
     *
     * @param userId:userId根据用户查询评论
     * @param page 页数
     * @param size 每页的数量
     * @return
     */
    public PaginationDto getNotification(Long userId, Integer page, Integer size) {
        Integer offset=(page-1) * size;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);

        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        notificationExample.setOrderByClause("gmt_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));
        if (notifications.size() == 0){
            return  null;
        }
//        Set<Long> disUserIds = notifications.stream().map(notify -> notify.getNotifier()).collect(Collectors.toSet());
//        ArrayList<Long> userIds = new ArrayList<>(disUserIds);
//        UserExample userExample = new UserExample();
//        userExample.createCriteria().andIdIn(userIds);
//        List<User> users = userMapper.selectByExample(userExample);
//        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(u -> u.getId(), u -> u));

        ArrayList<NotificationDto> notificationDtos = new ArrayList<>();
        for (Notification notification:notifications) {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            User user = userMapper.selectByPrimaryKey(userId);
            notificationDto.setNotifier(user);
            notificationDto.setType(notification.getType());
            notificationDto.setTypeName(NotificationEnum.nameofType(notification.getType()));
            notificationDtos.add(notificationDto);
        }

        PaginationDto<NotificationDto> paginationDto = new PaginationDto();
        NotificationExample notificationExample1 = new NotificationExample();
        notificationExample1.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int)notificationMapper.countByExample(notificationExample1);
        paginationDto.setPagination(totalCount,page,size);
        paginationDto.setData(notificationDtos);
        return paginationDto;
    }


    /**
     * @param userId 用户id
     * @return 该用户未读取的通知数量
     */
    public Long unReadCount(Long userId){
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverEqualTo(userId);
        long count = notificationMapper.countByExample(notificationExample);
        return count;
    }

    /**
     * 更改状态
     * @param id
     */
    public NotificationDto readStatus(Long id,User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);

        if( notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(notification.getReceiver() != user.getId()){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAILL);
        }
        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDto notificationDTO = new NotificationDto();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setType(notification.getType());
        notificationDTO.setTypeName(NotificationEnum.nameofType(notification.getType()));
        return notificationDTO;
    }
}


