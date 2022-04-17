package com.cloudinwind.selfmanage.entity.time;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * (Label)表实体类
 *
 * @author makejava
 * @since 2022-03-30 16:47:30
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


}

