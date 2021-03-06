package com.cloudinwind.selfmanage.controller.blog;

import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.blog.Blog;
import com.cloudinwind.selfmanage.entity.blog.Tag;
import com.cloudinwind.selfmanage.service.blog.BlogService;
import com.cloudinwind.selfmanage.service.blog.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String types(@PathVariable Long id, @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                        Model model, HttpServletRequest request){

        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();


        PageHelper.startPage(pagenum, 100);  //开启分页
        // 获取该用户下的标签
        List<Tag> tags = tagService.getUserBlogTag(userId);
        //-1从导航点过来的
        if (id == -1){
            id = tags.get(0).getId();
        }
        // 获取该标签下的所有博客
        List<Blog> blogs = blogService.getByUserTagId(userId, id);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("tags", tags);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeTagId", id);

        return "blog/userTags";
    }
}
