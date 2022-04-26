package com.cloudinwind.selfmanage.entity.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * (Type)表实体类
 *
 * @author makejava
 * @since 2022-04-24 11:40:20
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "tb_type")
public class Type extends Model<Type> {

    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;

    private List<Blog> blogs = new ArrayList<>();




}

