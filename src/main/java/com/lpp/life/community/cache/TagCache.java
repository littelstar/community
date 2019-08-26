package com.lpp.life.community.cache;

import com.lpp.life.community.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDto> get(){
        ArrayList<TagDto> tagDtos = new ArrayList<>();
        TagDto program  = new TagDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("java","javascript","css","html","node.js","python"));

        tagDtos.add(program);
        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring","express","flask","struts"));
        tagDtos.add(framework);
        return tagDtos;
    }


    public  static String filterInValid(String tags){
        String[] split = StringUtils.split(tags, ",");
        List<TagDto> tagDtos = get();
        List<String> tagList = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());

        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

        return invalid;
    }
}
