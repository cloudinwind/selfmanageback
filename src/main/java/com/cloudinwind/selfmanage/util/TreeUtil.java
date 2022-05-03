package com.cloudinwind.selfmanage.util;

import com.cloudinwind.selfmanage.entity.admin.Menu;
import com.cloudinwind.selfmanage.entity.time.Label;
import com.cloudinwind.selfmanage.entity.time.LayUiTree;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    // 这是一个将label列表转化为可以让前端识别的data的数据结构
    public static List<Label> getLabelsHasChild(List<Label> allLabels, Integer parentId){
        List<Label> labels = new ArrayList<>();
        for (Label label : allLabels) {
            if (label.getParentId().equals(parentId)) {
                labels.add(recursionLabel(label,allLabels));
            }
        }
        return labels;
    }

    /**
     * 递归获取所有孩子节点标签
     */
    private static Label recursionLabel(Label parentLabel, List<Label> labelList){
        List<Label> childLabel = new ArrayList<>();
        for (Label label : labelList) {
            if (label.getParentId().equals(parentLabel.getLabelId())) {
                childLabel.add(recursionLabel(label,labelList));
            }
        }
        parentLabel.setChildren(childLabel);
        return parentLabel;
    }

    /**
     * 通过labelList返回treeList
     */
    public static List<LayUiTree> getLabelTreeListFromLabel(List<Label> labels, Integer parentId){

        List<LayUiTree> layUiTreeList = new ArrayList<>();
        for (Label label : labels) {
            //判断传入的id是否有孩子,如果是，挂载在当前的节点上
            if (label.getParentId().equals(parentId)) {
                LayUiTree tree = getLayUiTreeFromLabel(label);
                //找当前节点的孩子，递归调用
                tree = recursion2(tree,labels);

                //添加到最终结果中
                layUiTreeList.add(tree);
            }
        }

        return layUiTreeList;
    }

    /**
     * 传入一个label，返回layuitree
     */
    private static LayUiTree getLayUiTreeFromLabel(Label label){
        LayUiTree treeNode = new LayUiTree();
        treeNode.setId(label.getLabelId());
        treeNode.setTitle(label.getLabelName());
//        treeNode.setUrl(menu.getUrl());
//        treeNode.setIcon(menu.getIcon());
        return treeNode;
    }

    /**
     * 递归调用，匹配孩子
     */
    private static LayUiTree recursion2(LayUiTree tree, List<Label> labelList){
        //定义一个集合，装孩子用的
        List<LayUiTree> childNodes = new ArrayList<>();
        for (Label node : labelList) {
            if (node.getParentId().equals(tree.getId())) {
                LayUiTree layUiTreeFromLabel = getLayUiTreeFromLabel(node);
                childNodes.add(recursion2(layUiTreeFromLabel,labelList));
            }
        }
        tree.setChildren(childNodes);
        return tree;
    }


    // 左侧导航菜单
    /**
     * 通过menuList返回treeList
     * @param menus
     * @param parentId
     * @return
     */
    public static List<LayUiTree> getMenuTreeListFromMenu(List<Menu> menus, Integer parentId){

        List<LayUiTree> layUiTreeList = new ArrayList<>();
        for (Menu menu : menus) {
            //判断传入的id是否有孩子,如果是，挂载在当前的节点上
            if (menu.getParentId().equals(parentId)) {
                LayUiTree tree = getLayUiTreeFromMenu(menu);
                //找当前节点的孩子，递归调用
                tree = recursion(tree,menus);

                //添加到最终结果中
                layUiTreeList.add(tree);
            }
        }

        return layUiTreeList;
    }

    /**
     * 递归调用，匹配孩子
     * @param tree
     * @param menuList
     * @return
     */
    private static LayUiTree recursion(LayUiTree tree, List<Menu> menuList){
        //定义一个集合，装孩子用的
        List<LayUiTree> childNodes = new ArrayList<>();
        for (Menu node : menuList) {
            if (node.getParentId().equals(tree.getId())) {
                LayUiTree layUiTreeFromMenu = getLayUiTreeFromMenu(node);
                childNodes.add(recursion(layUiTreeFromMenu,menuList));
            }
        }
        tree.setChildren(childNodes);
        return tree;
    }

    /**
     * 传入一个menu，返回layuitree
     * @param menu
     * @return
     */
    private static LayUiTree getLayUiTreeFromMenu(Menu menu){
        LayUiTree treeNode = new LayUiTree();
        treeNode.setId(menu.getMenuId());
        treeNode.setTitle(menu.getMenuName());
        treeNode.setUrl(menu.getUrl());
        treeNode.setIcon(menu.getIcon());
        return treeNode;
    }

    /**
     * 这是一个将menu列表转化为可以让前端识别的data的数据结构
     *     var inst1 = tree.render({
     *       elem: '#test1'  //绑定元素
     *       ,data: [{
     *         title: '江西' //一级菜单
     *         ,children: [{
     *           title: '南昌' //二级菜单
     *           ,children: [{
     *             title: '高新区' //三级菜单
     *             //…… //以此类推，可无限层级
     *           }]
     *         }]
     *       },{
     *         title: '陕西' //一级菜单
     *         ,children: [{
     *           title: '西安' //二级菜单
     *         }]
     *       }]
     *     });
     * @param allMenus 所有的菜单
     * @param parentId 根节点0
     * @return
     */
    public static List<Menu> getMenusHasChild(List<Menu> allMenus,Integer parentId){
        List<Menu> menus = new ArrayList<>();
        for (Menu menu : allMenus) {
            if (menu.getParentId().equals(parentId)) {
                menus.add(recursionMenu(menu,allMenus));
            }
        }
        return menus;
    }

    /**
     * 递归获取所有孩子节点菜单
     * @param parentMenu 上级父亲菜单
     * @param menuList 所有菜单
     * @return Menu返回带有孩子节点的菜单
     */
    private static Menu recursionMenu(Menu parentMenu, List<Menu> menuList){
        List<Menu> childMenu = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getParentId().equals(parentMenu.getMenuId())) {
                childMenu.add(recursionMenu(menu,menuList));
            }
        }
        parentMenu.setChildren(childMenu);
        return parentMenu;
    }

}
