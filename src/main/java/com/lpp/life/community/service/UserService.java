package com.lpp.life.community.service;

import com.lpp.life.community.dto.GithubUser;
import com.lpp.life.community.mapper.UserMapper;
import com.lpp.life.community.model.User;
import com.lpp.life.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public String creageOrUpdateUser(GithubUser githubUser){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(githubUser.getId().toString());
        List<User> users = userMapper.selectByExample(userExample);
        User user =new User();
        user.setLogin(githubUser.getLogin());
        user.setGmtModefied(System.currentTimeMillis());
        user.setAvatarUrl(githubUser.getAvatar_url());
        if(users.size() == 0){
            user.setAccountId(githubUser.getId().toString());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());
            userMapper.insert(user);
        }else{
            userMapper.updateByPrimaryKey(user);
        }
        return user.getToken();
    }
}
