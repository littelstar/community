package com.lpp.life.community.controller;

import com.lpp.life.community.dto.AcessTokenDto;
import com.lpp.life.community.dto.GithubUser;
import com.lpp.life.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
//    http://localhost:8080/callback?code=db8df56db385b137eafb&state=1
    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,@RequestParam(name = "state")String state){

        AcessTokenDto acessTokenDto = new AcessTokenDto();
        acessTokenDto.setClient_id("Iv1.6e9bde8ffa4f09e4");
        acessTokenDto.setClient_secret("b343b987b33db7698ef03ab8b3883c1ceb391bad");
        acessTokenDto.setRedirect_uri("http://localhost:8080/callback");
        acessTokenDto.setCode(code);
        acessTokenDto.setState(state);
        String acessToken = githubProvider.getAcessToken(acessTokenDto);
        GithubUser user = githubProvider.getUser(acessToken);
        System.out.println(user);
        return "index";
    }
}
