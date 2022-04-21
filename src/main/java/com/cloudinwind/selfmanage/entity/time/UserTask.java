package com.cloudinwind.selfmanage.entity.time;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * (UserTask)表实体类
 */
@TableName("time_user_task")
public class UserTask extends Model<UserTask> {
    
    private Integer userId;
    
    private Integer taskId;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

}

