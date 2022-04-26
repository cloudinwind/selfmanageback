package com.cloudinwind.selfmanage.entity.blog;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * (UserTag)表实体类
 *
 * @author makejava
 * @since 2022-04-24 21:15:56
 */
@SuppressWarnings("serial")
@TableName(value = "tb_user_tag")
public class UserTag extends Model<UserTag> {
    
    private Long userId;
    
    private Long tagId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

}

