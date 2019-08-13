package com.lpp.life.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String login;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModefied;
    private String avatarUrl;
}
