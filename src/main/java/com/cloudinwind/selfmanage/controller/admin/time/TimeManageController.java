package com.cloudinwind.selfmanage.controller.admin.time;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudinwind.selfmanage.controller.time.BaseController;
import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.time.Label;
import com.cloudinwind.selfmanage.entity.time.ReturnBean;
import com.cloudinwind.selfmanage.service.time.LabelService;
import com.cloudinwind.selfmanage.service.time.TaskService;
import com.cloudinwind.selfmanage.service.time.UserTaskService;
import com.cloudinwind.selfmanage.util.DateUtils;
import com.cloudinwind.selfmanage.vo.admin.LabelVo;
import com.cloudinwind.selfmanage.vo.time.TaskVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/time")
public class TimeManageController extends BaseController {

    @Resource
    TaskService taskService;

    @Resource
    LabelService labelService;


    @RequestMapping("taskAnalysis")
    public ReturnBean taskAnalysis(){

        TaskVo taskVo = new TaskVo();

        // 查询增长的任务数量
        taskVo.setStartDateCondition(DateUtils.getPastDate(6));
        taskVo.setEndDateCondition(DateUtils.getPastDate(0));

        List<TaskVo> taskVoListAll = taskService.selectTaskGroupByUpdateTime(taskVo);

        // 查询增长的标签数量
        LabelVo labelVo = new LabelVo();

        labelVo.setStartDateCondition(DateUtils.getPastDate(6));
        labelVo.setEndDateCondition(DateUtils.getPastDate(0));
        List<LabelVo> labelVos = labelService.selectLabelGroupByUpdateTime(labelVo);


        Map<Integer, Map<String, Integer>> mapHashMap= new HashMap<>();
        Map<String, Integer> mapTask = new HashMap<>();
        Map<String, Integer> mapLabel = new HashMap<>();

        // 初始化
        for (int i=0; i<=6; i++){
            mapTask.put(DateUtils.getPastDate(i), 0);
            mapLabel.put(DateUtils.getPastDate(i), 0);
        }

        for (TaskVo taskVo1: taskVoListAll){
            mapTask.put(DateUtils.dateToStr(taskVo1.getUpdateTime()), taskVo1.getSumDuration());
        }

        for (LabelVo labelVo1: labelVos){
            mapLabel.put(DateUtils.dateToStr(labelVo1.getUpdateTime()), labelVo1.getSumDuration());
        }

        mapHashMap.put(0, mapTask);
        mapHashMap.put(1, mapLabel);

        return success(mapHashMap);

    }

    @RequestMapping("selectAllTaskGroupByLabel")
    public ReturnBean selectAllTaskGroupByLabel(@RequestParam(required = false, value = "nowDate")String nowDate,
                                                    HttpServletRequest request){

        TaskVo taskVo = new TaskVo();

        List<TaskVo> taskVos = taskService.selectAllTaskGroupByLabel(taskVo);

        return success(taskVos, "success");
    }

}
