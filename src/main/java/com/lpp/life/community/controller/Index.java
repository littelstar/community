package com.lpp.life.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Index {

    @RequestMapping("/")
    public String test(Model model){
//        model.addAttribute("name",name);
        return "index";
    }
}
