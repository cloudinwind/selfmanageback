package com.cloudinwind.selfmanage.controller.blog;

import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.blog.Blog;
import com.cloudinwind.selfmanage.entity.blog.Tag;
import com.cloudinwind.selfmanage.entity.blog.Type;
import com.cloudinwind.selfmanage.service.blog.BlogService;
import com.cloudinwind.selfmanage.service.blog.TagService;
import com.cloudinwind.selfmanage.service.blog.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BlogIndexController {

    @Resource
    private BlogService blogService;

    @Resource
    private TypeService typeService;

    @Resource
    private TagService tagService;

    // 个人博客展示首页
    @GetMapping("/blog")
    public String toIndex(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                          Model model, HttpServletRequest request){

        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        PageHelper.startPage(pagenum, 5);
        // 获取该用户下的所有博客
        List<Blog> allBlog = blogService.getUserBlog(userId);
        // 获取该用户下的分类 (连表查询)
        List<Type> allType = typeService.getUserBlogType(userId);
        // 获取该用户下的标签
        List<Tag> allTag = tagService.getUserBlogTag(userId);  //获取博客的标签(联表查询)
        // 获取获得推荐的博客
        List<Blog> recommendBlog =blogService.getAllUserRecommendBlog(userId);  //获取推荐博客

        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", allTag);
        model.addAttribute("types", allType);
        model.addAttribute("recommendBlogs", recommendBlog);
        return "blog/userIndex";

    }

    // 个人博客管理搜索页面
    @PostMapping("/blog/search")
    public String search(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                         @RequestParam String query, Model model, HttpServletRequest request){

        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        PageHelper.startPage(pagenum, 20);
        List<Blog> searchBlog = blogService.getUserSearchBlog(userId, query);
        PageInfo pageInfo = new PageInfo(searchBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "blog/userSearch";
    }

    // 博客详情页
    @GetMapping("/blog/{id}")
    public String toLogin(@PathVariable("id") Long id, Model model){
        Blog blog = blogService.getDetailedBlog(id);
        model.addAttribute("blog", blog);
        return "blog/blogDetail";
    }
}
