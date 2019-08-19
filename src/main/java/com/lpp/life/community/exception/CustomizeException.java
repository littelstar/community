package com.lpp.life.community.exception;

public class CustomizeException extends  RuntimeException {

    private String message;
    public CustomizeException(CustomizeErrorCode code){
        this.message=code.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
