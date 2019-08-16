package com.lpp.life.community.controller;

import com.lpp.life.community.dto.AcessTokenDto;
import com.lpp.life.community.dto.GithubUser;
import com.lpp.life.community.mapper.UserMapper;
import com.lpp.life.community.provider.GithubProvider;
import com.lpp.life.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    private UserService userService;
//    http://localhost:8080/callback?code=db8df56db385b137eafb&state=1
    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code")String code, @RequestParam(name = "state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){

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
            String token = userService.creageOrUpdateUser(githubUser);
//            添加cookie
            response.addCookie(new Cookie("token",token));
//            request.getSession().setAttribute("user",githubUser);
//            重定向返回的是连接
            return "redirect:/";

        }else {
            return "redirect:/";
        }

    }
    @GetMapping("/logout")
    public String logOut(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie token = new Cookie("token", null);
        token.setMaxAge(0);
        response.addCookie(token);
        return "redirect:/";
    }
}
