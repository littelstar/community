package com.lpp.life.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Hello {

    @RequestMapping("/hello")
    public String test(@RequestParam(name = "name")String name,Model model){
        model.addAttribute("name",name);

        return "hello";
    }
}
