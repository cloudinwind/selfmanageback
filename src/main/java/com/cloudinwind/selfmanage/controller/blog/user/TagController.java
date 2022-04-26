package com.cloudinwind.selfmanage.controller.blog.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.blog.Tag;
import com.cloudinwind.selfmanage.entity.blog.UserTag;
import com.cloudinwind.selfmanage.service.blog.TagService;
import com.cloudinwind.selfmanage.service.blog.UserTagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/blog/user")
public class TagController {

    @Autowired
    TagService tagService;

    @Autowired
    UserTagService userTagService;

    // // 跳转到 知识管理后台 标签管理页面
    @GetMapping("/tags")
    public String tags(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                       Model model, HttpServletRequest request){

        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();


        PageHelper.startPage(pagenum, 5);
        List<Tag> allTag = tagService.getUserAllTag(userId);
        //得到分页结果对象
        PageInfo<Tag> pageInfo = new PageInfo<>(allTag);
        model.addAttribute("pageInfo", pageInfo);
        return "blog/user/tags";
    }

    // 跳转到知识管理后台  添加标签页面
    @GetMapping("/tags/input")
    public String toAddTag(Model model){
        model.addAttribute("tag", new Tag());   //返回一个tag对象给前端th:object
        return "blog/user/tags-input";
    }

    // 跳转到知识管理后台  编辑标签页面
    @GetMapping("/tags/{id}/input")
    public String toEditTag(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTagById(id));
        return "blog/user/tags-input";
    }

    // 提交 添加标签请求
    @PostMapping("/tags")
    public String addTag(Tag tag, RedirectAttributes attributes, HttpServletRequest request){   //新增
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();


        Tag t = tagService.getTagByName(tag.getName());
        if(t != null){  // 该标签已存在
            // 判断该用户是否拥有该标签
            QueryWrapper<UserTag> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            queryWrapper.eq("tag_id", t.getId());
            int cnt = userTagService.count(queryWrapper);
            if (cnt > 0){   // 说明该标签已经存在于该用户下, 不需要再重复添加
                attributes.addFlashAttribute("msg", "不能添加重复的标签");
                return "redirect:/blog/user/tags/input";
            }else{  // 不然只需要添加到关联表
                attributes.addFlashAttribute("msg", "添加成功");
                // 添加到关联表
                UserTag userTag = new UserTag();
                userTag.setTagId(t.getId());
                userTag.setUserId(userId);
                userTagService.save(userTag);
                return "redirect:/blog/user/tags";
            }

        }else { // 该标签根本不存在

            attributes.addFlashAttribute("msg", "添加成功");
            // 添加到标签表
            tagService.saveTag(tag);

            t = tagService.getTagByName(tag.getName());
            // 添加到关联表
            UserTag userTag = new UserTag();
            userTag.setTagId(t.getId());
            userTag.setUserId(userId);
            userTagService.save(userTag);

            return "redirect:/blog/user/tags";   //不能直接跳转到tags页面，否则不会显示tag数据(没经过tags方法)
        }

    }

    // 提交 更新标签请求
    @PostMapping("/tags/{id}")
    public String editTag(@PathVariable Long id, Tag tag, RedirectAttributes attributes,
                          HttpServletRequest request){  //修改
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();


        Tag t = tagService.getUserTagByName(tag.getName(), userId);
        if(t != null){
            attributes.addFlashAttribute("msg", "不能添加重复的标签");
            return "redirect:/blog/user/tags/input";
        }else {
            attributes.addFlashAttribute("msg", "修改成功");
        }
        tagService.updateTag(tag);
        return "redirect:/blog/user/tags";   //不能直接跳转到tags页面，否则不会显示tag数据(没经过tags方法)
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes,
                         HttpServletRequest request){
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        // 删除关联表
        QueryWrapper<UserTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("tag_id", id);
        userTagService.remove(queryWrapper);

        // 删除标签
        tagService.deleteTag(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/blog/user/tags";
    }
}
