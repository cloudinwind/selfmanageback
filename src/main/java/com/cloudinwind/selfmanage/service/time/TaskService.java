package com.cloudinwind.selfmanage.service.time;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudinwind.selfmanage.entity.time.Task;
import com.cloudinwind.selfmanage.vo.time.TaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Task)表服务接口
 *
 * @author makejava
 * @since 2022-03-30 15:22:51
 */
public interface TaskService extends IService<Task> {

    List<TaskVo> selectByPage(Integer page, Integer limit, TaskVo taskVo);

    List<TaskVo> selectUserTask(TaskVo taskVo);

    Integer countTask(TaskVo taskVo);


    List<TaskVo> selectUserTaskGroupByLabelDay(TaskVo taskVo);
    List<TaskVo> selectUserTaskGroupByPriorityDay(TaskVo taskVo);

    List<TaskVo> selectUserTaskGroupByLabelTime(TaskVo taskVo);
    List<TaskVo> selectUserTaskGroupByPriorityTime(TaskVo taskVo);
    List<TaskVo> selectUserTaskGroupByUpdateTime(TaskVo taskVo);
    List<TaskVo> selectFinishedUserTaskGroupByUpdateTime(TaskVo taskVo);



    Integer selectTaskDuration( TaskVo taskVo);

    List<TaskVo> selectTaskGroupByUpdateTime(TaskVo taskVo);

    List<TaskVo> selectAllTaskGroupByLabel(TaskVo taskVo);

}

