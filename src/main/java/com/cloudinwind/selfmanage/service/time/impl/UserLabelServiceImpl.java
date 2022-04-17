package com.cloudinwind.selfmanage.service.time.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.entity.time.UserLabel;
import com.cloudinwind.selfmanage.mapper.time.UserLabelDao;
import com.cloudinwind.selfmanage.service.time.UserLabelService;
import org.springframework.stereotype.Service;

/**
 * (UserLabel)表服务实现类
 *
 * @author makejava
 * @since 2022-03-29 20:57:37
 */
@Service("userLabelService")
public class UserLabelServiceImpl extends ServiceImpl<UserLabelDao, UserLabel> implements UserLabelService {

}

