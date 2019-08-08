package com.lpp.life.community.mapper;

import com.lpp.life.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert into user(login,account_id,token,gmt_create,gmt_modefied) values(#{login},#{accountId},#{token},#{gmtCreate},#{gmtModefied})")
    public void insertUser(User user);
}
