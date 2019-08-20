package com.lpp.life.community.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long parentId;
    private String content;
    private Integer type;
}
