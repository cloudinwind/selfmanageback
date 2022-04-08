package com.cloudinwind.selfmanage.mapper;

import com.cloudinwind.selfmanage.dto.UserQueryDTO;
import com.cloudinwind.selfmanage.entity.User;

import java.util.List;

public interface UserExtMapper {
    List<User> selectLatestLoginUser(UserQueryDTO userQueryDTO);
    Integer count(UserQueryDTO userQueryDTO);
}
