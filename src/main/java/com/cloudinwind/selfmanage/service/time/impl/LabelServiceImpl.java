package com.cloudinwind.selfmanage.service.time.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.entity.time.Label;
import com.cloudinwind.selfmanage.mapper.time.LabelDao;
import com.cloudinwind.selfmanage.service.time.LabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Label)表服务实现类
 *
 * @author makejava
 * @since 2022-03-30 16:47:33
 */
@Service("labelService")
public class LabelServiceImpl extends ServiceImpl<LabelDao, Label> implements LabelService {

    @Resource
    LabelDao labelDao;

    @Override
    public List<Label> selectByUserId(Integer userId) {
        return labelDao.selectByUserId(userId);
    }
}

