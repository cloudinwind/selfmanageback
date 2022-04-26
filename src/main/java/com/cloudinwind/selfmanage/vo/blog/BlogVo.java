package com.cloudinwind.selfmanage.vo.blog;

import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.entity.blog.Tag;
import com.cloudinwind.selfmanage.entity.blog.Type;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class BlogVo {

    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentabled;
    private boolean published;
    private boolean recommend;
    private Date createTime;
    private Date updateTime;

    //这个属性用来在mybatis中进行连接查询的
    private Long typeId;
    private Long userId;

    //获取多个标签的id
    private String tagIds;
    private String description;

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
