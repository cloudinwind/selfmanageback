package com.cloudinwind.selfmanage.vo.admin;

import lombok.Data;

import java.util.Date;

@Data
public class UserAccountVo {
    private Long id;

    private Long userId;


    private Integer groupId;


    private Integer vipRank;


    private Integer score;


    private Integer score1;


    private Integer score2;

    private Integer score3;

    private String nowDate;

    private String startDateCondition;

    private String endDateCondition;

    private Integer countUser;

    private Date createTime;

}
