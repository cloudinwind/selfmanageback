package com.cloudinwind.selfmanage.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudinwind.selfmanage.entity.admin.Menu;
import com.cloudinwind.selfmanage.entity.time.LayUiTree;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2021-12-06 15:34:41
 */
public interface MenuService extends IService<Menu> {
    public List<LayUiTree>  queryAllMenusByUserId(Integer userId);

    /**
     * 查询用户所有的权限
     * @param userId
     * @return
     */
    public Set<String> queryAllPermsByUserId(Integer userId);

}

