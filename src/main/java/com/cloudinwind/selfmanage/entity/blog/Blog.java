package com.cloudinwind.selfmanage.entity.blog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.cloudinwind.selfmanage.entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (Blog)表实体类
 *
 * @author makejava
 * @since 2022-04-24 11:16:07
 */
@Data
@TableName("tb_blog")
public class Blog extends Model<Blog> {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String title;
    
    private String content;
    
    private String firstPicture;
    
    private String flag;
    
    private Integer views;
    
    private Integer appreciation;
    
    private Integer shareStatement;
    
    private Integer commentabled;
    
    private Integer published;
    
    private Integer recommend;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Long typeId;
    
    private Long userId;
    
    private String description;
    
    private String tagIds;



    private Type type;

    private User user;

    private List<Tag> tags = new ArrayList<>();

    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }

    //将tags集合转换为tagIds字符串形式：“1,2,3”,用于编辑博客时显示博客的tag
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for(Tag tag: tags){
                if(flag){
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }




}

