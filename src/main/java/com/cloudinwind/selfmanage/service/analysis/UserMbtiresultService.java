package com.cloudinwind.selfmanage.service.analysis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.entity.analysis.UserMbtiresult;
import com.cloudinwind.selfmanage.vo.analysis.MbtiResultVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (UserMbtiresult)表服务接口
 *
 * @author makejava
 * @since 2022-04-27 21:28:16
 */
public interface UserMbtiresultService extends IService<UserMbtiresult> {

    List<MbtiResultVo> selectAllMbtiResult(Integer page, Integer limit, User user);

}

