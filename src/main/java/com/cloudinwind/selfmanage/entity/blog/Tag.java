package com.cloudinwind.selfmanage.entity.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * (Tag)表实体类
 *
 * @author makejava
 * @since 2022-04-24 11:34:12
 */
@SuppressWarnings("serial")
@Data
@TableName(value = "tb_tag")
public class Tag extends Model<Tag> {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private List<Blog> blogs = new ArrayList<>();


}

