package com.cloudinwind.selfmanage.mapper;

import com.cloudinwind.selfmanage.dto.LikeQueryDTO;
import com.cloudinwind.selfmanage.entity.forum.Comment;
import com.cloudinwind.selfmanage.entity.forum.Question;

public interface ThumbExtMapper {
    int incLikeCount(Comment comment);

    int incQuestionLikeCount(Question question);

    Integer count(LikeQueryDTO likeQueryDTO);
}
