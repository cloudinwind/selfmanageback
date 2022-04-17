package com.cloudinwind.selfmanage.service.time.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.mapper.time.UserTaskDao;
import com.cloudinwind.selfmanage.entity.time.UserTask;
import com.cloudinwind.selfmanage.service.time.UserTaskService;
import org.springframework.stereotype.Service;

/**
 * (UserTask)表服务实现类
 *
 * @author makejava
 * @since 2022-03-30 15:28:59
 */
@Service("userTaskService")
public class UserTaskServiceImpl extends ServiceImpl<UserTaskDao, UserTask> implements UserTaskService {

}

