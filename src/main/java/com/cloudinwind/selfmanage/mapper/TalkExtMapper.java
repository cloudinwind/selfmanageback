package com.cloudinwind.selfmanage.mapper;

import com.cloudinwind.selfmanage.dto.TalkQueryDTO;
import com.cloudinwind.selfmanage.entity.forum.Talk;

public interface TalkExtMapper {

    Integer count(TalkQueryDTO talkQueryDTO);

    int updateByPrimaryKeySelective(Talk talk);
}
