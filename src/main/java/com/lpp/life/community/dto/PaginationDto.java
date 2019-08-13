package com.lpp.life.community.dto;

import com.lpp.life.community.mapper.QuestionMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDto {


    private List<QuestionDto> questionDtos;
    private Boolean showPre;
    private Boolean showNext;
    private Boolean showEnd;
    private Boolean showFirst;
    private Integer pageNum;
    private Integer currentPage;
    private List<Integer> integers = new ArrayList<>();

    public void setPagination(Integer totalCount, Integer offset, Integer size) {
//        设置分页总数
        if (totalCount % size == 0) {
            pageNum = totalCount / size;
        } else {
            pageNum = totalCount / size + 1;
        }

//        设置当前页数
        if(offset % size == 0){
            currentPage=offset/size;
        }else{
            currentPage = offset/size + 1;
        }

        if (offset == 1) {
            showPre = false;
        }
        if (offset == totalCount) {
            showEnd = false;
        }
        if (offset != 1 && offset != totalCount) {
            showPre = true;
            showEnd = true;
        }

        if (offset < size) {
            for (int i = 0; i < size && i < pageNum; i++) {
                Integer integer = new Integer(i + 1);
                integers.add(integer);
            }
        } else {
            for (int i = 0; i < size / 2 && i < pageNum/2; i++) {
                Integer integer = new Integer(size - i);
                integers.add(integer);
            }
            for (int i = 0; i < size / 2 && i < pageNum/2; i++) {
                Integer integer = new Integer(size + i);
                integers.add(integer);
            }
        }
        Integer begin = (offset - 1) * size;


    }

}
