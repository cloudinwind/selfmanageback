package com.cloudinwind.selfmanage.mapper;

import com.cloudinwind.selfmanage.entity.UserAccount;
import com.cloudinwind.selfmanage.vo.admin.UserAccountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserAccountExtMapper {

    int incScore(UserAccount userAccount);
    int decScore(UserAccount userAccount);

    List<UserAccountVo> getAllUserAccount(@Param("userAccountVo") UserAccountVo userAccountVo);

    List<UserAccountVo> getAllUserAccountGroupByTime(@Param("userAccountVo") UserAccountVo userAccountVo);
}
