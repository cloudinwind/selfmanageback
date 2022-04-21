package com.cloudinwind.selfmanage.util;

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

}
