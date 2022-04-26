package com.cloudinwind.selfmanage.entity.blog;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * (UserType)表实体类
 *
 * @author makejava
 * @since 2022-04-24 21:14:57
 */
@SuppressWarnings("serial")
@TableName(value = "tb_user_type")
public class UserType extends Model<UserType> {
    
    private Long userId;
    
    private Long typeId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

}

