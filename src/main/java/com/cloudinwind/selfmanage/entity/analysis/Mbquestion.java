package com.cloudinwind.selfmanage.entity.analysis;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * (Mbquestion)表实体类
 *
 * @author makejava
 * @since 2022-04-27 19:44:10
 */
@SuppressWarnings("serial")
@TableName("ta_mbquestion")
public class Mbquestion extends Model<Mbquestion> {

    @TableId(type = IdType.AUTO)
    private Integer id;
    //mbti表中问题题目
    private String question;
    // 选项A
    private String optionA;
    // 选项B
    private String optionB;
    //类别 1表示E/I; 2表示S/N; 3表示T/F; 4表示J/P
    private Integer type;
    //记录是否可用 0表示可用;1表示不可用
    private Integer status;
    
    private Integer flag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
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

