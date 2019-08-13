package com.lpp.life.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private String login;
    private Long id;
    private String bio;
    private String avatar_url;

    @Override
    public String toString() {
        return "GithubUser{" +
                "login='" + login + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }
}
