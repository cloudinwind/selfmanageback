package com.cloudinwind.selfmanage.entity.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * (AdminAnnounce)表实体类
 *
 * @author makejava
 * @since 2022-05-03 10:39:03
 */
@SuppressWarnings("serial")
@Data
@TableName("admin_announce")
public class AdminAnnounce extends Model<AdminAnnounce> {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private Date createTime;

    private Date updateTime;

    private String content;


}

