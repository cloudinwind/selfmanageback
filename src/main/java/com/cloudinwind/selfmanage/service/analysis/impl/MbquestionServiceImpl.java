package com.cloudinwind.selfmanage.service.analysis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.entity.analysis.Mbquestion;
import com.cloudinwind.selfmanage.mapper.analysis.MbquestionDao;
import com.cloudinwind.selfmanage.service.analysis.MbquestionService;
import org.springframework.stereotype.Service;

/**
 * (Mbquestion)表服务实现类
 *
 * @author makejava
 * @since 2022-04-27 19:44:10
 */
@Service("mbquestionService")
public class MbquestionServiceImpl extends ServiceImpl<MbquestionDao, Mbquestion> implements MbquestionService {

}

