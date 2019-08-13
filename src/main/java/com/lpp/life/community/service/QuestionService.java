package com.lpp.life.community.service;

import com.lpp.life.community.dto.QuestionDto;
import com.lpp.life.community.mapper.QuestionMapper;
import com.lpp.life.community.mapper.UserMapper;
import com.lpp.life.community.model.Question;
import com.lpp.life.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public ArrayList<QuestionDto> getQuestionDto(Integer page,Integer size){
        Integer offset=(page-1) * size;
        List<Question> questions = questionMapper.getQuestion(offset,size);
        ArrayList<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            User user = userMapper.fingById(question.getCreator());
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }

        return questionDtos;
    }
}
