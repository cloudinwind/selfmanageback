package com.cloudinwind.selfmanage.mapper;

import com.cloudinwind.selfmanage.dto.TalkQueryDTO;
import com.cloudinwind.selfmanage.entity.forum.Talk;

/**
 * @author wadao
 * @version 1.0
 * @date 2020/9/24 21:53
 * @site niter.cn
 */
public interface TalkExtMapper {

    Integer count(TalkQueryDTO talkQueryDTO);

    int updateByPrimaryKeySelective(Talk talk);
}
