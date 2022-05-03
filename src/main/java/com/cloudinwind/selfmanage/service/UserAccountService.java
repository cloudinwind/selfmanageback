package com.cloudinwind.selfmanage.service;


import com.cloudinwind.selfmanage.entity.UserAccount;
import com.cloudinwind.selfmanage.entity.UserAccountExample;
import com.cloudinwind.selfmanage.mapper.UserAccountExtMapper;
import com.cloudinwind.selfmanage.mapper.UserAccountMapper;
import com.cloudinwind.selfmanage.vo.admin.UserAccountVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserAccountService {

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private UserAccountExtMapper userAccountExtMapper;


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

    public List<UserAccountVo> getAllUserAccount(UserAccountVo userAccountVo){
        return userAccountExtMapper.getAllUserAccount(userAccountVo);
    }

    public List<UserAccountVo> getAllUserAccountGroupByTime(UserAccountVo userAccountVo){
        return userAccountExtMapper.getAllUserAccountGroupByTime(userAccountVo);
    }




}

