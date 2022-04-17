package com.cloudinwind.selfmanage.mapper.time;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudinwind.selfmanage.entity.time.Label;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Label)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-30 16:47:30
 */
public interface LabelDao extends BaseMapper<Label> {



    List<Label> selectByUserId(@Param("userId") Integer userId);

}

