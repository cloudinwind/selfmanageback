package com.cloudinwind.selfmanage.mapper.analysis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.entity.analysis.UserMbtiresult;
import com.cloudinwind.selfmanage.vo.analysis.MbtiResultVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (UserMbtiresult)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-27 21:28:16
 */
public interface UserMbtiresultDao extends BaseMapper<UserMbtiresult> {


    List<MbtiResultVo> selectAllMbtiResult(@Param("page")Integer page, @Param("limit")Integer limit, @Param("user")User user);

}

