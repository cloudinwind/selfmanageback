package com.cloudinwind.selfmanage.service.blog.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.entity.blog.BlogTag;
import com.cloudinwind.selfmanage.mapper.blog.BlogTagDao;
import com.cloudinwind.selfmanage.service.blog.BlogTagService;
import org.springframework.stereotype.Service;

/**
 * (BlogTag)表服务实现类
 *
 * @author makejava
 * @since 2022-04-24 11:27:06
 */
@Service("blogTagService")
public class BlogTagServiceImpl extends ServiceImpl<BlogTagDao, BlogTag> implements BlogTagService {

}

