package com.cloudinwind.selfmanage.service.blog;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudinwind.selfmanage.entity.blog.Tag;

import java.util.List;

/**
 * (Tag)表服务接口
 *
 * @author makejava
 * @since 2022-04-24 11:34:12
 */
public interface TagService extends IService<Tag> {

    // 获取该用户下的所有标签(包含该标签下的博客)
    List<Tag> getUserBlogTag(Long userId);

    // 获取该用户下的所有标签(不包含该标签下的博客)
    List<Tag> getUserAllTag(Long userId);

    List<Tag> getTagByString(String text);   //从字符串中获取tag集合

    Tag getTagById(Long id);

    Tag getUserTagByName(String name, Long userId);

    int saveTag(Tag tag);

    int updateTag(Tag tag);

    int deleteTag(Long id);

    // 查询有无该标签(该标签不一定属于该用户)
    Tag getTagByName(String name);

}

