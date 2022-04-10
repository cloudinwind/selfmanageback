package com.cloudinwind.selfmanage.enums;

// 定义帖子排序(分类)方式
public enum SortEnum {
    HOT,    // 普通排序  显示所有帖子
    HOT30,  // 月榜     显示一个月内发布的帖子
    HOT7,   // 周榜     显示一周内发布的帖子
    NO,     // 抢沙发(没有评论的帖子), 增加曝光度  显示没有评论的帖子
    NEW,    // 按最新排序    默认            按照时间倒序排序
    GOOD;   // 精华排序     精华帖           显示加精的帖子 status=1 or status=3
}
