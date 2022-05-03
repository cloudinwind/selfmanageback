package com.cloudinwind.selfmanage.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudinwind.selfmanage.entity.admin.Menu;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2021-12-06 15:34:40
 */
public interface MenuDao extends BaseMapper<Menu> {

    List<Menu> queryAllMenusByUserId(Integer userId);

    Set<String> queryAllPermsByUserId(Integer userId);
}

