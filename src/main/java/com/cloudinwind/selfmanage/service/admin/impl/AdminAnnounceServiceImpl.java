package com.cloudinwind.selfmanage.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.entity.admin.AdminAnnounce;
import com.cloudinwind.selfmanage.mapper.admin.AdminAnnounceDao;
import com.cloudinwind.selfmanage.service.admin.AdminAnnounceService;
import org.springframework.stereotype.Service;

/**
 * (AdminAnnounce)表服务实现类
 *
 * @author makejava
 * @since 2022-05-03 10:39:03
 */
@Service("adminAnnounceService")
public class AdminAnnounceServiceImpl extends ServiceImpl<AdminAnnounceDao, AdminAnnounce> implements AdminAnnounceService {

}

