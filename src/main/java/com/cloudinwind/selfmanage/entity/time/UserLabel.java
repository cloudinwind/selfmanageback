package com.cloudinwind.selfmanage.entity.time;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * (UserLabel)表实体类
 *
 * @author makejava
 * @since 2022-03-29 20:57:37
 */
@TableName("time_user_label")
public class UserLabel extends Model<UserLabel> {
    
    private Integer userId;
    
    private Integer labelId;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

}

