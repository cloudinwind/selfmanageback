package com.cloudinwind.selfmanage.service.time;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudinwind.selfmanage.entity.time.Label;

import java.util.List;

/**
 * (Label)表服务接口
 *
 * @author makejava
 * @since 2022-03-30 16:47:32
 */
public interface LabelService extends IService<Label> {

    List<Label> selectByUserId(Integer userId);

}

