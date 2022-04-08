package com.cloudinwind.selfmanage.mapper;

import com.cloudinwind.selfmanage.dto.CommentQueryDTO;
import com.cloudinwind.selfmanage.entity.forum.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);

    Integer countBySearch(CommentQueryDTO commentQueryDTO);

}
