package com.cloudinwind.selfmanage.service.blog.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloudinwind.selfmanage.entity.blog.Blog;
import com.cloudinwind.selfmanage.entity.blog.BlogTag;
import com.cloudinwind.selfmanage.entity.blog.Tag;
import com.cloudinwind.selfmanage.mapper.blog.BlogDao;
import com.cloudinwind.selfmanage.mapper.blog.BlogTagDao;
import com.cloudinwind.selfmanage.service.blog.BlogService;
import com.cloudinwind.selfmanage.util.MarkdownUtils;
import com.cloudinwind.selfmanage.vo.blog.BlogVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * (Blog)表服务实现类
 *
 * @author makejava
 * @since 2022-04-24 11:16:07
 */
@Service("blogService")
public class BlogServiceImpl extends ServiceImpl<BlogDao, Blog> implements BlogService {

    @Resource
    BlogDao blogDao;

    @Resource
    BlogTagDao blogTagDao;

    @Override
    public List<Blog> getUserBlog(Long userId) {
        return blogDao.getUserBlog(userId);
    }

    @Override
    public List<Blog> getAllUserRecommendBlog(Long userId) {
        return blogDao.getAllUserRecommendBlog(userId);
    }

    @Override
    public List<Blog> getByUserTypeId(Long userId, Long typeId) {
        return blogDao.getByUserTypeId(userId, typeId);
    }

    @Override
    public List<Blog> getByUserTagId(Long userId, Long tagId) {
        return blogDao.getByUserTagId(userId, tagId);
    }

    @Override
    public Map<String, List<Blog>> archiveUserBlog(Long userId) {
        List<String> years = blogDao.findUserBlogGroupYear(userId);
        Set<String> set = new HashSet<>(years);  //set去掉重复的年份
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : set) {
            map.put(year, blogDao.findUserBlogByYear(userId, year));
        }
        return map;
    }

    @Override
    public int countUserBlog(Long userId) {
        return blogDao.getUserAllBlog(userId).size();
    }

    @Override
    public List<Blog> getUserSearchBlog(Long userId, String query) {
        return blogDao.getUserSearchBlog(userId, query);
    }

    @Override
    public Blog getDetailedBlog(Long id) {
        Blog blog = blogDao.getDetailedBlog(id);
        if (blog == null) {
            System.out.println("该博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));  //将Markdown格式转换成html
        return blog;
    }

    @Override
    public List<Blog> getUserAllBlog(Long userId) {
        return blogDao.getUserAllBlog(userId);
    }

    // 后台根据标题、分类、推荐搜索博客
    @Override
    public List<Blog> searchUserAllBlog(Blog blog) {
        return blogDao.searchUserAllBlog(blog);
    }

    // 根据blogId查找博客
    @Override
    public Blog getBlogById(Long id) {
        return blogDao.getBlogById(id);
    }

    @Override    //新增博客
    public int saveBlog(BlogVo blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        //保存博客
        blogDao.saveBlog(blog);
        //保存博客后才能获取自增的id
        Long id = blog.getId();
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogTag blogTag = null;
        for (Tag tag : tags) {
            blogTag = new BlogTag();
            blogTag.setTagId(tag.getId());
            blogTag.setBlogId(blog.getId());
            blogTagDao.insert(blogTag);
        }
        return 1;
    }

    @Override   //编辑博客
    public int updateBlog(BlogVo blog) {
        blog.setUpdateTime(new Date());
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogTag blogTag = null;
        for (Tag tag : tags) {
            blogTag = new BlogTag();
            blogTag.setTagId(tag.getId());
            blogTag.setBlogId(blog.getId());
            blogTagDao.insert(blogTag);
        }
        return blogDao.updateBlog(blog);
    }
}

