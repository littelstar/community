package com.lpp.life.community.mapper;

import com.lpp.life.community.model.Question;
import com.lpp.life.community.service.QuestionService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{creator})")
    public void insertQuestion(Question question);

    @Select("SELECT * FROM question")
    public List<Question> getQuestion();
}
