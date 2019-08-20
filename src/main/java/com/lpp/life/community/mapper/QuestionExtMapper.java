package com.lpp.life.community.mapper;

import com.lpp.life.community.model.Question;

public interface QuestionExtMapper {

     void incView(Question question);
     void incComment(Question question);
}
