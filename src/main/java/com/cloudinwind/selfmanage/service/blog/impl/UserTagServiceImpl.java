package com.cloudinwind.selfmanage.service.blog.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.entity.blog.UserTag;
import com.cloudinwind.selfmanage.mapper.blog.UserTagDao;
import com.cloudinwind.selfmanage.service.blog.UserTagService;
import org.springframework.stereotype.Service;

/**
 * (UserTag)表服务实现类
 *
 * @author makejava
 * @since 2022-04-24 21:15:56
 */
@Service("userTagService")
public class UserTagServiceImpl extends ServiceImpl<UserTagDao, UserTag> implements UserTagService {

}

