package com.lpp.life.community.mapper;

import com.lpp.life.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(login,account_id,token,gmt_create,gmt_modefied,avatar_url) values(#{login},#{accountId},#{token},#{gmtCreate},#{gmtModefied},#{avatar_Url})")
    public void insertUser(User user);

    @Select("SELECT * FROM `user` WHERE token=#{token}")
    User findByToken(@Param("token") String token);
    @Select("SELECT * FROM user WHERE id=#{id}")
    User fingById(@Param("id") Integer id);
}
