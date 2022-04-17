package com.cloudinwind.selfmanage.controller.time;

import com.cloudinwind.selfmanage.entity.time.Label;
import com.cloudinwind.selfmanage.entity.time.ReturnBean;
import com.cloudinwind.selfmanage.service.time.LabelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Label)表控制层
 *
 * @author makejava
 * @since 2022-03-30 16:47:28
 */
@RestController
@RequestMapping("label")
public class LabelController extends BaseController {

    @Resource
    LabelService labelService;

    @RequestMapping("selectLabelByUserId")
    public ReturnBean selectLabelByUserId(@RequestParam("userId")Integer userId)
    {
        List<Label> labels = labelService.selectByUserId(userId);
        long count = labels.size();

        return success(labels, count);
    }
}