package com.cloudinwind.selfmanage.entity.time;

import lombok.Data;

import java.util.List;

@Data
public class LayUiTree {

    /**
     * tree节点的名称
     */
    private String title;
    private int id;
    /**
     * 属性
     */
    private String field;
    /**
     * 是否选中
     */
    private boolean checked;
    private boolean spread;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 图标
     */
    private String icon;

    private List<LayUiTree> children;
}
