package com.lpp.life.community.enums;

public enum NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
        REPLY_COMMENT(2,"回复了评论");
    private Integer type;
    private String name;

    public static String nameofType(Integer type){
        for (NotificationEnum notificationEnum : NotificationEnum.values()) {
            if(type == notificationEnum.getType()){
                return notificationEnum.getName();
            }
        }
        return null;
    }

     NotificationEnum(Integer status,String name){
        this.type =status;
        this.name=name;
    }
    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
