package com.cloudinwind.selfmanage.mapper;

import com.cloudinwind.selfmanage.dto.NewsQueryDTO;
import com.cloudinwind.selfmanage.entity.forum.News;

import java.util.List;

public interface NewsExtMapper {
    int incView(News record);
    Integer countBySearch(NewsQueryDTO newsQueryDTO);
    List<News> selectBySearch(NewsQueryDTO newsQueryDTO);

}
