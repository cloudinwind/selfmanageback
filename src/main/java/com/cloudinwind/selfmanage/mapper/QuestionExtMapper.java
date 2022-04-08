package com.cloudinwind.selfmanage.mapper;

import com.cloudinwind.selfmanage.dto.QuestionQueryDTO;
import com.cloudinwind.selfmanage.entity.forum.Question;

import java.util.List;

public interface QuestionExtMapper {
    int incView(Question record);

    int incCommentCount(Question record);

    List<Question> selectRelated(Question question);

    List<Question> selectTop(QuestionQueryDTO questionQueryDTO);


    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
