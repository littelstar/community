package com.lpp.life.community.mapper;

import com.lpp.life.community.model.Question;
import com.lpp.life.community.service.QuestionService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator})")
    public void insertQuestion(Question question);

    @Select("SELECT * FROM question limit #{offset},#{size}")
    public List<Question> getQuestion(@Param("offset") Integer offset,@Param("size") Integer size);

    @Select("SELECT count(1) FROM question")
    Integer getCount();

    @Select("SELECT * FROM question WHERE creator =#{userId} LIMIT #{offset},#{size}")
    List<Question> getQuestionByUserId(@Param("userId")Integer userId, @Param("offset")Integer offset, @Param("size") Integer size);

    @Select("SELECT count(1) FROM question WHERE creator =#{userId}")
    Integer getCountByUserID( @Param("userId")Integer userId);
}
