package com.cloudinwind.selfmanage.dto;

import lombok.Data;

@Data
public class QuestionQueryDTO {
    private String search;  // 查询条件
    private String tag;     // 标签
    private String sort;    // 排序方式(分类方式)
    private Long time;
    private Integer page;
    private Integer offset;
    private Integer size;
    private Integer column2;

}
