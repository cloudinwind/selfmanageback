package com.cloudinwind.selfmanage.mapper.blog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloudinwind.selfmanage.entity.blog.Blog;
import com.cloudinwind.selfmanage.vo.blog.BlogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Blog)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-24 11:16:07
 */
public interface BlogDao extends BaseMapper<Blog> {

    // 获取该用户下的所有博客
    List<Blog> getUserBlog(@Param("userId")Long userId);

    List<Blog> getAllUserRecommendBlog(@Param("userId")Long userId);

    // 查询该用户该分类下的所有博客
    List<Blog> getByUserTypeId(@Param("userId")Long userId, @Param("typeId")Long typeId);

    // 查询该用户该标签下的所有博客
    List<Blog> getByUserTagId(@Param("userId")Long userId, @Param("tagId")Long tagId);


    // 查询该用户发表博客的所有年份
    List<String> findUserBlogGroupYear(@Param("userId")Long userId);

    // 查询该用户对应年份发表的博客
    List<Blog> findUserBlogByYear(@Param("userId")Long userId, @Param("year") String year);

    // 查询该用户发布的所有文章, 不包含用户信息
    List<Blog> getUserAllBlog(@Param("userId")Long userId);


    // 搜索当前用户发布的满足条件的博客
    List<Blog> getUserSearchBlog(@Param("userId")Long userId, @Param("query") String query);

    // 根据博客id查找博客详情
    Blog getDetailedBlog(@Param("id") Long id);

    // 后台根据标题、分类、推荐搜索博客
    List<Blog> searchUserAllBlog(Blog blog);

    // 根据博客id查找博客
    Blog getBlogById(@Param("id") Long id);

    int saveBlog(BlogVo blog);

    int updateBlog(BlogVo blog);
}

