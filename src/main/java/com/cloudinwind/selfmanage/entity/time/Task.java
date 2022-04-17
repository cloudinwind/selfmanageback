package com.cloudinwind.selfmanage.entity.time;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * (Task)表实体类
 *
 * @author makejava
 * @since 2022-03-30 15:22:51
 */
@Data
@TableName("time_task")
public class Task extends Model<Task> {
    @TableId(type = IdType.AUTO)
    private Integer taskId;
    
    private Integer priority;
    //任务开始时间
    private Date startTime;
    //任务结束时间
    private Date endTime;
    
    private String taskContent;
    //时间段 如08:00-09:00
    private String timePeriod;
    //标记是否完成,0未完成
    private Integer finished;
    //标签外键
    private Integer labelId;

    private String childTask;
    
    private Date createTime;
    
    private Date updateTime;
    //任务说明
    private String explan;
    // 判断是否为父任务
    private Integer parentId;
    // 任务持续时间
    private Integer duration;

    private Integer flag;
}

