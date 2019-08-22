package com.lpp.life.community.dto;

import com.lpp.life.community.exception.CustomizeErrorCode;
import com.lpp.life.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDto <T>{
    private Integer code;
    private String message;
    private T content;
    public static ResultDto errorOf(CustomizeErrorCode error){
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(error.getCode());
        resultDto.setMessage(error.getMessage());
        return resultDto;
    }

    public static ResultDto successOf(CustomizeErrorCode success) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(success.getCode());
        resultDto.setMessage(success.getMessage());
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeException ex) {

        ResultDto resultDto = new ResultDto();
        resultDto.setCode(ex.getCode());
        resultDto.setMessage(ex.getMessage());
        return resultDto;
    }

    public static <T>ResultDto successOf(T t) {
        ResultDto<Object> objectResultDto = new ResultDto<>();
        objectResultDto.setCode(200);
        objectResultDto.setMessage("请求成功");
        objectResultDto.setContent(t);
        return objectResultDto;
    }
}
