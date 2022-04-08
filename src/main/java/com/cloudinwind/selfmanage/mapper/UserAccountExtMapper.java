package com.cloudinwind.selfmanage.mapper;

import com.cloudinwind.selfmanage.entity.UserAccount;

public interface UserAccountExtMapper {

    int incScore(UserAccount userAccount);
    int decScore(UserAccount userAccount);
}
