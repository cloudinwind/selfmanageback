package com.cloudinwind.selfmanage.service.blog.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.entity.blog.UserType;
import com.cloudinwind.selfmanage.mapper.blog.UserTypeDao;
import com.cloudinwind.selfmanage.service.blog.UserTypeService;
import org.springframework.stereotype.Service;

/**
 * (UserType)表服务实现类
 *
 * @author makejava
 * @since 2022-04-24 21:14:57
 */
@Service("userTypeService")
public class UserTypeServiceImpl extends ServiceImpl<UserTypeDao, UserType> implements UserTypeService {

}

