package com.lpp.life.community.controller;

import com.lpp.life.community.dto.FileDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {

    @ResponseBody
    @RequestMapping("/file/upload")
    public Object upload(){
        FileDto fileDto = new FileDto();
        fileDto.setMessage("图片");
        fileDto.setSuccess(1);
        fileDto.setUrl("/images/loading.gif");
        return fileDto;
    }
}
