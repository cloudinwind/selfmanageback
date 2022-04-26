package com.cloudinwind.selfmanage.service.blog.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.entity.blog.UserBlog;
import com.cloudinwind.selfmanage.mapper.blog.UserBlogDao;
import com.cloudinwind.selfmanage.service.blog.UserBlogService;
import org.springframework.stereotype.Service;

/**
 * (UserBlog)表服务实现类
 *
 * @author makejava
 * @since 2022-04-24 11:40:53
 */
@Service("userBlogService")
public class UserBlogServiceImpl extends ServiceImpl<UserBlogDao, UserBlog> implements UserBlogService {

}

