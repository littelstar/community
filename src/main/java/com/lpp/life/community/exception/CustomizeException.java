package com.lpp.life.community.exception;

public class CustomizeException extends  RuntimeException {

    private String message;
    private Integer code;
    public CustomizeException(CustomizeErrorCode errorCode){
        this.message = errorCode.getMessage();
        this.code =errorCode.getCode();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
