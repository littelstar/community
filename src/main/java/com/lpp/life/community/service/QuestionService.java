package com.lpp.life.community.service;

import com.lpp.life.community.dto.QuestionDto;
import com.lpp.life.community.exception.CustomizeErrorCode;
import com.lpp.life.community.exception.CustomizeException;
import com.lpp.life.community.mapper.QuestionMapper;
import com.lpp.life.community.mapper.UserMapper;
import com.lpp.life.community.model.Question;
import com.lpp.life.community.model.QuestionExample;
import com.lpp.life.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
        List<Question> questions= questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
//        List<Question> questions = questionMapper.getQuestion(offset,size);
        ArrayList<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }

        return questionDtos;
    }

    public ArrayList<QuestionDto>  getQuestionDtoByUserId(Integer userId, Integer page, Integer size) {
        Integer offset=(page-1) * size;
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample,new RowBounds(offset,size));
        ArrayList<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }

        return questionDtos;
    }

    public QuestionDto getQuestionById(Integer id) {
        QuestionDto questionDto = new QuestionDto();
        Question question= questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
        }
        BeanUtils.copyProperties(question,questionDto);
        return questionDto;
    }
    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else{
            question.setGmtModified(System.currentTimeMillis());
            int updated = questionMapper.updateByPrimaryKeySelective(question);
            if(updated!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
            }
        }
    }
}
