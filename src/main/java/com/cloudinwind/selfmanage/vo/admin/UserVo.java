package com.cloudinwind.selfmanage.vo.admin;

import lombok.Data;

@Data
public class UserVo {

    private Long id;
    private String name;

    private String password;
    private String avatarUrl;

    private String email;

    private String phone;

    private Integer groupId;
    private String groupStr;

    private String startDateCondition;

    private String endDateCondition;

//    private String updateBy;


}
