package com.cloudinwind.selfmanage.service;


import com.cloudinwind.selfmanage.entity.UserAccount;
import com.cloudinwind.selfmanage.entity.UserAccountExample;
import com.cloudinwind.selfmanage.mapper.UserAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountMapper userAccountMapper;


    public UserAccount selectUserAccountByUserId(Long userId) {
      //  Long id = Long.parseLong(userId);
        UserAccountExample userAccountExample = new UserAccountExample();
        userAccountExample.createCriteria().andUserIdEqualTo(userId);
        List<UserAccount> userAccounts = userAccountMapper.selectByExample(userAccountExample);
        UserAccount userAccount = userAccounts.get(0);
        return userAccount;
    }

    public boolean isAdminByUserId(Long userId){
        if(selectUserAccountByUserId(userId).getGroupId()>=18) return true;
        else return false;

    }

}

