package com.cloudinwind.selfmanage.controller.blog.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.blog.Type;
import com.cloudinwind.selfmanage.entity.blog.UserType;
import com.cloudinwind.selfmanage.service.blog.TypeService;
import com.cloudinwind.selfmanage.service.blog.UserTypeService;
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
public class TypeController {

    @Autowired
    TypeService typeService;

    @Autowired
    UserTypeService userTypeService;

    // 跳转到 知识管理后台 分类管理页面
    @GetMapping("/types")
    public String types(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                        Model model, HttpServletRequest request){

        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        PageHelper.startPage(pagenum, 5);
        List<Type> allType = typeService.getUserAllType(userId);
        //得到分页结果对象
        PageInfo<Type> pageInfo = new PageInfo<>(allType);
        model.addAttribute("pageInfo", pageInfo);
        return "blog/user/types";
    }

    // 跳转到知识管理后台  添加分类页面
    @GetMapping("/types/input")
    public String toAddType(Model model){
        model.addAttribute("type", new Type());   //返回一个type对象给前端th:object
        return "blog/user/types-input";
    }

    // 跳转到知识管理后台  编辑分类页面
    @GetMapping("/types/{id}/input")
    public String toEditType(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getTypeById(id));
        return "blog/user/types-input";
    }

    // 提交 添加分类请求
    @PostMapping("/types")
    public String addType(Type type, RedirectAttributes attributes,
                          HttpServletRequest request){   //新增
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        Type t = typeService.getTypeByName(type.getName());
        if(t != null){
            // 判断该用户是否拥有该分类
            QueryWrapper<UserType> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            queryWrapper.eq("type_id", t.getId());
            int cnt = userTypeService.count(queryWrapper);
            if (cnt > 0){   // 说明该分类已经存在于该用户下, 不需要再重复添加
                attributes.addFlashAttribute("msg", "不能添加重复的分类");
                return "redirect:/blog/user/types/input";
            }else{  // 不然只需要添加到关联表
                attributes.addFlashAttribute("msg", "添加成功");
                // 添加到关联表
                UserType userType = new UserType();
                userType.setUserId(userId);
                userType.setTypeId(t.getId());
                userTypeService.save(userType);
                return "redirect:/blog/user/types";
            }


        }else {
            attributes.addFlashAttribute("msg", "添加成功");
            typeService.saveType(type);

            t = typeService.getTypeByName(type.getName());
            // 更新关联表
            UserType userType = new UserType();
            userType.setUserId(userId);
            userType.setTypeId(t.getId());
            userTypeService.save(userType);

            return "redirect:/blog/user/types";   //不能直接跳转到types页面，否则不会显示type数据(没经过types方法)
        }

    }

    // 提交 更新分类请求
    @PostMapping("/types/{id}")
    public String editType(@PathVariable Long id, Type type, RedirectAttributes attributes,
                           HttpServletRequest request){  //修改
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        Type t = typeService.getUserTypeByName(type.getName(), userId);
        if(t != null){
            attributes.addFlashAttribute("msg", "不能添加重复的分类");
            return "redirect:/blog/user/types/input";
        }else {
            attributes.addFlashAttribute("msg", "修改成功");
        }
        typeService.updateType(type);
        return "redirect:/blog/user/types";   //不能直接跳转到types页面，否则不会显示type数据(没经过types方法)
    }

    // 删除分类
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes,
                         HttpServletRequest request){
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();
        // 删除关联表
        QueryWrapper<UserType> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("type_id", id);
        userTypeService.remove(queryWrapper);

        // 删除分类
        typeService.deleteType(id);

        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/blog/user/types";
    }
}
