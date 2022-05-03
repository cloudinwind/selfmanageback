package com.cloudinwind.selfmanage.mapper;

import com.cloudinwind.selfmanage.dto.UserQueryDTO;
import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.vo.admin.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserExtMapper {
    List<User> selectLatestLoginUser(UserQueryDTO userQueryDTO);


    Integer count(UserQueryDTO userQueryDTO);

    List<User> selectAdminUser(@Param("userQueryDTO") UserQueryDTO userQueryDTO);

    List<UserVo> selectUserAll(@Param("page")Integer page, @Param("limit")Integer limit,
            @Param("userVo") UserVo userVo);
}
