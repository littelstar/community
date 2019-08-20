package com.lpp.life.community.advice;

import com.alibaba.fastjson.JSON;
import com.lpp.life.community.dto.ResultDto;
import com.lpp.life.community.exception.CustomizeErrorCode;
import com.lpp.life.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    Object handleControllerException(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        if (request.getContentType().equals("application/json")) {
            ResultDto resultDto = null;
            if (ex instanceof CustomizeException) {
                resultDto = ResultDto.errorOf((CustomizeException) ex);
            } else {
                resultDto = ResultDto.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = null;
            try {

                writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (ex instanceof CustomizeException) {
            model.addAttribute("message", ex.getMessage());
        } else {
            model.addAttribute("message", "服务器忙碌中");

        }
        return new ModelAndView("error");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
