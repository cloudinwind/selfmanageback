package com.cloudinwind.selfmanage.controller.blog.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.entity.blog.Blog;
import com.cloudinwind.selfmanage.entity.blog.BlogTag;
import com.cloudinwind.selfmanage.service.blog.BlogService;
import com.cloudinwind.selfmanage.service.blog.BlogTagService;
import com.cloudinwind.selfmanage.service.blog.TagService;
import com.cloudinwind.selfmanage.service.blog.TypeService;
import com.cloudinwind.selfmanage.vo.blog.BlogVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/blog/user")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogTagService blogTagService;

    @GetMapping()
    public String loginPage(){
        return "blog/user/index";
    }

    public void setTypeAndTag(Long userId, Model model) {
        model.addAttribute("types", typeService.getUserAllType(userId));
        model.addAttribute("tags", tagService.getUserAllTag(userId));
    }

    //用户知识管理后台 显示博客列表 博客管理
    @GetMapping("/blogs")
    public String blogs(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                        Model model, HttpServletRequest request){

        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        PageHelper.startPage(pagenum, 5);
        List<Blog> allBlog = blogService.getUserAllBlog(userId);
        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(userId, model);  //查询类型和标签
        return "blog/user/blogs";
    }



    @PostMapping("/blogs/search")  //按条件查询博客
    public String searchBlogs(Blog blog, @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                              Model model, HttpServletRequest request){

        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        blog.setUserId(userId);

        PageHelper.startPage(pagenum, 5);
        List<Blog> allBlog = blogService.searchUserAllBlog(blog);
        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("message", "查询成功");
        setTypeAndTag(userId, model);
        return "blog/user/blogs";
    }

    // 跳转到新增博客页面
    @GetMapping("/blogs/input")
    public String toAddBlog(Model model, HttpServletRequest request){
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        model.addAttribute("blog", new Blog());  //返回一个blog对象给前端th:object
        setTypeAndTag(userId, model);
        return "blog/user/blogs-input";
    }

    // 跳转到编辑博客页面
    @GetMapping("/blogs/{id}/input") //去编辑博客页面
    public String toEditBlog(@PathVariable Long id,
                             Model model, HttpServletRequest request){
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        Blog blog = blogService.getBlogById(id);
        blog.init();   //将tags集合转换为tagIds字符串
        model.addAttribute("blog", blog);     //返回一个blog对象给前端th:object
        setTypeAndTag(userId, model);
        return "blog/user/blogs-input";
    }

    // 保存新增/修改后的博客
    @PostMapping("/blogs") //新增、编辑博客
    public String addBlog(BlogVo blog, HttpSession session,
                          RedirectAttributes attributes, HttpServletRequest request){

        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        User user = new User();
        user.setId(loginUser.getId());
        user.setName(loginUser.getName());
        user.setAvatarUrl(loginUser.getAvatarUrl());

        //设置user属性
        blog.setUser(user);
        //设置用户id
        blog.setUserId(blog.getUser().getId());
        //设置blog的type
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        //设置blog中typeId属性
        blog.setTypeId(blog.getType().getId());
        //给blog中的List<Tag>赋值
        blog.setTags(tagService.getTagByString(blog.getTagIds()));

        if (blog.getId() == null) {   //id为空，则为新增
            blogService.saveBlog(blog);
        } else {
            blogService.updateBlog(blog);
        }

        attributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/blog/user/blogs";
    }

    // 删除博客
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlogs(@PathVariable Long id, RedirectAttributes attributes){
        // 删除关联表
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", id);
        blogTagService.remove(queryWrapper);

        // 删除博客
        blogService.removeById(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/blog/user/blogs";
    }


}
