package com.lpp.life.community.controller;

import com.lpp.life.community.dto.FileDto;
import com.lpp.life.community.provider.UcloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@Controller
public class FileController {

    @Autowired
    private UcloudProvider ucloudProvider;

    @ResponseBody
    @RequestMapping("/file/upload")
    public Object upload(HttpServletRequest request){

        MultipartHttpServletRequest multipartHttpServletRequest= (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("editor-image-file");
        try {
            String filename = ucloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
        }catch (IOException e){
            e.printStackTrace();
        }
        FileDto fileDto = new FileDto();
        fileDto.setMessage("图片");
        fileDto.setSuccess(1);
        fileDto.setUrl("/");
        return fileDto;
    }
}
