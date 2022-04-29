package com.cloudinwind.selfmanage.service.analysis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.entity.analysis.UserMbtiresult;
import com.cloudinwind.selfmanage.mapper.analysis.UserMbtiresultDao;
import com.cloudinwind.selfmanage.service.analysis.UserMbtiresultService;
import com.cloudinwind.selfmanage.vo.analysis.MbtiResultVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (UserMbtiresult)表服务实现类
 *
 * @author makejava
 * @since 2022-04-27 21:28:16
 */
@Service("userMbtiresultService")
public class UserMbtiresultServiceImpl extends ServiceImpl<UserMbtiresultDao, UserMbtiresult> implements UserMbtiresultService {

    @Resource
    UserMbtiresultDao userMbtiresultDao;

    @Override
    public List<MbtiResultVo> selectAllMbtiResult(Integer page, Integer limit, User user) {
        return userMbtiresultDao.selectAllMbtiResult(page, limit, user);
    }
}

