package com.lpp.life.community.mapper;

import com.lpp.life.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

     void incView(Question question);
     void incComment(Question question);
     List<Question> selectRelate(Question question);
}
