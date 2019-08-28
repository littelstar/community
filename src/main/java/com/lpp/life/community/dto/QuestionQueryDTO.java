package com.lpp.life.community.dto;

import lombok.Data;

/**
 * 搜索问题参数封装类
 */
@Data
public class QuestionQueryDTO {

    private String search;
    private String tags;
    private Integer page;
    private Integer size;
}
