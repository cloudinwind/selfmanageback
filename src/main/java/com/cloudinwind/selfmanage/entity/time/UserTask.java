package com.cloudinwind.selfmanage.entity.time;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * (UserTask)表实体类
 */
@Data
@TableName("time_user_task")
public class UserTask extends Model<UserTask> {
    
    private Integer userId;
    
    private Integer taskId;

    private Date createTime;

    private Date updateTime;


}

