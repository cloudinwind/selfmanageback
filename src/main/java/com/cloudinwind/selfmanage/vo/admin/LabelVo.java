package com.cloudinwind.selfmanage.vo.admin;

import lombok.Data;

import java.util.Date;

@Data
public class LabelVo {

    private Integer labelId;

    private String labelName;

    private String labelColor;

    private Integer parentId;

    private Integer flag;

    private Date createTime;

    private Date updateTime;

    private String startDateCondition;

    private String endDateCondition;


    private Integer sumDuration;

}
