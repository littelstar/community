package com.lpp.life.community.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUNT("问题不存在");
    private String message;
    CustomizeErrorCode(String message){
        this.message=message;
    }
    @Override
    public String getMessage() {
        return this.message;
    }
}
