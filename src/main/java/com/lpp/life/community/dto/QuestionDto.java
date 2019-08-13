package com.lpp.life.community.dto;

import com.lpp.life.community.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tags;
    private User user;
}
