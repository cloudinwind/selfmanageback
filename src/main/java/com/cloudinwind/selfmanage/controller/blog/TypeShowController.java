package com.cloudinwind.selfmanage.controller.blog;

import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.blog.Blog;
import com.cloudinwind.selfmanage.entity.blog.Type;
import com.cloudinwind.selfmanage.service.blog.BlogService;
import com.cloudinwind.selfmanage.service.blog.TypeService;
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
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                        Model model, HttpServletRequest request){
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        PageHelper.startPage(pagenum, 10);  //开启分页
        // 获取该用户下的所有分类 (连表查询)
        List<Type> types = typeService.getUserBlogType(userId);

        //-1从导航点过来的
        if (id == -1){
            id = types.get(0).getId();
        }

        // 查询该用户该分类下的所有博客
        List<Blog> blogs = blogService.getByUserTypeId(userId, id);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("types", types);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("activeTypeId", id);
        return "blog/userTypes";
    }
}
