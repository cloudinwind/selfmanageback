package com.cloudinwind.selfmanage.entity.blog;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * (UserBlog)表实体类
 *
 * @author makejava
 * @since 2022-04-24 11:40:53
 */
@SuppressWarnings("serial")
@TableName(value = "tb_user_blog")
public class UserBlog extends Model<UserBlog> {
    
    private Long userId;
    
    private Long blogId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

}

