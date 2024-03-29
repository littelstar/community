package com.lpp.life.community.service;

import com.lpp.life.community.dto.PaginationDto;
import com.lpp.life.community.dto.QuestionDto;
import com.lpp.life.community.dto.QuestionQueryDTO;
import com.lpp.life.community.exception.CustomizeErrorCode;
import com.lpp.life.community.exception.CustomizeException;
import com.lpp.life.community.mapper.QuestionExtMapper;
import com.lpp.life.community.mapper.QuestionMapper;
import com.lpp.life.community.mapper.UserMapper;
import com.lpp.life.community.model.Question;
import com.lpp.life.community.model.QuestionExample;
import com.lpp.life.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDto getQuestionDto(Integer page,Integer size,String search){
        Integer offset=(page-1) * size;
        if(StringUtils.isNotBlank(search)){
            String[] tags = StringUtils.split(search, " ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        questionQueryDTO.setSearch(search);
        List<Question> questions= questionExtMapper.selectBySearch(questionQueryDTO);
        ArrayList<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        PaginationDto paginationDto = new PaginationDto();
        Integer totalCount = questionExtMapper.selectCountBySearch(questionQueryDTO);
        paginationDto.setPagination(totalCount,page,size);
        paginationDto.setData(questionDtos);
        return paginationDto;
    }

    public PaginationDto  getQuestionDtoByUserId(Long userId, Integer page, Integer size) {
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

        PaginationDto<QuestionDto> paginationDto = new PaginationDto();
        QuestionExample questionExample2 = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount  = (int)questionMapper.countByExample(questionExample2);
        paginationDto.setPagination(totalCount,page,size);
        paginationDto.setData(questionDtos);

        return paginationDto;
    }

    public QuestionDto getQuestionById(Long id) {
        QuestionDto questionDto = new QuestionDto();
        Question question= questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUNT);
        }
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }
    public void createOrUpdate(Question question) {
        if(question.getId() == null){
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
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

    public void incView(Long id) {
        Question question = new Question();
        question.setViewCount(1);
        question.setId(id);

        questionExtMapper.incView(question);
    }

    public List<Question> selectRelated(QuestionDto questionDto){
        Question question = new Question();
        BeanUtils.copyProperties(questionDto,question);
        String replace = question.getTags().replace(",", "|");
        question.setTags(replace);
        List<Question> questions = questionExtMapper.selectRelate(question);
        return questions;
    }
}
