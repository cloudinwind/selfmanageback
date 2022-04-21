package com.cloudinwind.selfmanage.entity.time;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * (Label)表实体类
 */
@Data
@TableName("time_label")
public class Label extends Model<Label> {

    @TableId(type = IdType.AUTO)
    private Integer labelId;
    
    private String labelName;
    
    private String labelColor;
    
    private Integer parentId;

    private Integer flag;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private List<Label> children;

}

