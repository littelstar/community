package com.lpp.life.community.dto;

import com.lpp.life.community.model.User;
import lombok.Data;

@Data
public class NotificationDto {

    private Long id;
    private Long gmtCreate;
    private Integer status;
    private User notifier;
    private String outerTitle;
    private String notifierTitle;
    private Integer type;
    private String typeName;
}
