package com.lpp.life.community.controller;

import com.lpp.life.community.mapper.UserMapper;
import com.lpp.life.community.dto.AcessTokenDto;
import com.lpp.life.community.dto.GithubUser;
import com.lpp.life.community.model.User;
import com.lpp.life.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;
//    http://localhost:8080/callback?code=db8df56db385b137eafb&state=1
    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code")String code, @RequestParam(name = "state")String state, HttpServletRequest request){

        AcessTokenDto acessTokenDto = new AcessTokenDto();
        acessTokenDto.setClient_id(clientId);
        acessTokenDto.setClient_secret(clientSecret);
        acessTokenDto.setRedirect_uri(redirectUri);
        acessTokenDto.setCode(code);
        acessTokenDto.setState(state);
        String accessToken = githubProvider.getAcessToken(acessTokenDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
//        登录成功
        if(githubUser!=null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(githubUser.getId().toString());
            user.setLogin(githubUser.getLogin());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModefied(System.currentTimeMillis());
            userMapper.insertUser(user);
            request.getSession().setAttribute("user",githubUser);
//            重定向返回的是连接
            return "redirect:/";

        }else {
            return "redirect:/";
        }

    }
}
