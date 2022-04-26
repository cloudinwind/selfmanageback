package com.cloudinwind.selfmanage.service.blog;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloudinwind.selfmanage.entity.blog.Blog;
import com.cloudinwind.selfmanage.vo.blog.BlogVo;

import java.util.List;
import java.util.Map;

/**
 * (Blog)表服务接口
 *
 * @author makejava
 * @since 2022-04-24 11:16:07
 */
public interface BlogService extends IService<Blog> {

    List<Blog> getUserBlog(Long userId);

    List<Blog> getAllUserRecommendBlog(Long userId);

    // 查询该用户该分类下的所有博客
    List<Blog> getByUserTypeId(Long userId, Long typeId);

    // 查询该用户该标签下的所有博客
    List<Blog> getByUserTagId(Long userId, Long tagId);

    //用户博客归档
    Map<String,List<Blog>> archiveUserBlog(Long userId);

    // 查询该用户博客数量
    int countUserBlog(Long userId);

    // 搜索当前用户发布的满足条件的博客
    List<Blog> getUserSearchBlog(Long userId, String query);

    // 查找博客详情
    Blog getDetailedBlog(Long id);

    // 查询该用户发布的所有文章, 不包含用户信息
    List<Blog> getUserAllBlog(Long userId);

    // 后台根据标题、分类、推荐搜索博客
    List<Blog> searchUserAllBlog(Blog blog);

    // 根据blogId查找博客
    Blog getBlogById(Long id);

    int saveBlog(BlogVo blog);

    int updateBlog(BlogVo blog);


}

