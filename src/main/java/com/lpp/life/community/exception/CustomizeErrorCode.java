package com.lpp.life.community.exception;

public enum  CustomizeErrorCode implements ICustomizeErrorCode {
    QUESTION_NOT_FOUNT( 2001,"问题不存在"),
    COMMENT_PARENT_NOT_FOUNT(2002,"评论不对"),
    USER_NOT_LOGIN(2003,"未登录，请登录后操作"),
    SUCCESS_COMMNET(2004,"评论成功"),
    SYSTEM_ERROR(2005,"服务器出错了"),
    TYPE_PARAM_WRONG(2006,"评论类型错误"),
    COMMENT_NOT_FOUNT(2007,"评论未找到");
    private String message;
    private Integer code;
    CustomizeErrorCode(Integer code, String message){
        this.message = message;
        this.code = code;
    }
    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
