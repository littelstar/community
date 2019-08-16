package com.lpp.life.community.interceptor;

import com.lpp.life.community.mapper.UserMapper;
import com.lpp.life.community.model.User;
import com.lpp.life.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies==null){
            return true;
        }

        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                UserExample userExample = new UserExample();
                userExample.createCriteria().andTokenEqualTo(cookie.getValue());
                List<User> users = userMapper.selectByExample(userExample);
//                User byToken = userMapper.findByToken(cookie.getValue());
                if(users.size()!=0){
                    request.getSession().setAttribute("user",users.get(0));
                }else {
                    return false;
                }
                break;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
