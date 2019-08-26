package com.lpp.life.community.controller;

import com.lpp.life.community.dto.NotificationDto;
import com.lpp.life.community.enums.NotificationEnum;
import com.lpp.life.community.mapper.NotificationMapper;
import com.lpp.life.community.model.User;
import com.lpp.life.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/get/{id}")
    public String profile(@PathVariable(value = "id") Long id, HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
//        得到信息
        NotificationDto notificationDto = notificationService.readStatus(id, user);
        if(notificationDto.getType() == NotificationEnum.REPLY_COMMENT.getType()|| notificationDto.getType() == NotificationEnum.REPLY_COMMENT.getType()){
            return "redirect:/question/"+notificationDto.getOuterTitle();
        }
//        标记已读
        return  "redirect:/question/"+id;

    }
}
