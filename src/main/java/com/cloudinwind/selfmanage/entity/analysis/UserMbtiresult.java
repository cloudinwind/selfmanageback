package com.cloudinwind.selfmanage.entity.analysis;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (UserMbtiresult)表实体类
 *
 * @author makejava
 * @since 2022-04-27 21:28:16
 */
@SuppressWarnings("serial")
@TableName("ta_user_mbtiresult")
public class UserMbtiresult extends Model<UserMbtiresult> {

    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private Integer userId;
    
    private Integer questionId;
    
    private String answer;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    }

