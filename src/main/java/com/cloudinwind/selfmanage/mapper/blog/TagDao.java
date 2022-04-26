package com.cloudinwind.selfmanage.mapper.blog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudinwind.selfmanage.entity.blog.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Tag)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-24 11:34:12
 */
public interface TagDao extends BaseMapper<Tag> {

    // 获取该用户下的标签(包含该标签下的博客)
    List<Tag> getUserBlogTag(@Param("userId") Long userId);

    // 获取该用户下的所有标签(不包含该标签下的博客)
    List<Tag> getUserAllTag(@Param("userId") Long userId);

    Tag getTagById(Long id);

    // 根据标签名称获取该用户下对应的标签
    Tag getUserTagByName(@Param("name") String name, @Param("userId")Long userId);

    int saveTag(Tag tag);

    int updateTag(Tag tag);

    int deleteTag(Long id);

    // 查询有无该标签(该标签不一定属于该用户)
    Tag getTagByName(String name);
}

