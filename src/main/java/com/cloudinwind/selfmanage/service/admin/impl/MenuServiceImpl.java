package com.cloudinwind.selfmanage.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.entity.admin.Menu;
import com.cloudinwind.selfmanage.entity.time.LayUiTree;
import com.cloudinwind.selfmanage.mapper.admin.MenuDao;
import com.cloudinwind.selfmanage.service.admin.MenuService;
import com.cloudinwind.selfmanage.util.TreeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2021-12-06 15:34:41
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Resource
    private MenuDao menuDao;
    @Override
    public List<LayUiTree> queryAllMenusByUserId(Integer userId) {
        //根据用户id查询所有的menus对象
        List<Menu> menus = menuDao.queryAllMenusByUserId(userId);
        //将menuList转换成LayUiTreeList
        //0代表顶级菜单，或者根节点
        List<LayUiTree> menuTreeListFromMenu = TreeUtil.getMenuTreeListFromMenu(menus, 0);
        return menuTreeListFromMenu;
    }

    @Override
    public Set<String> queryAllPermsByUserId(Integer userId) {
        //写sql语句
        Set<String> strings = menuDao.queryAllPermsByUserId(userId);
        return strings;
    }
}

