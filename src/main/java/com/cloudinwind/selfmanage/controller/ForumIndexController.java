package com.cloudinwind.selfmanage.controller;

import com.cloudinwind.selfmanage.cache.HotTagCache;
import com.cloudinwind.selfmanage.cache.LoginUserCache;
import com.cloudinwind.selfmanage.dto.PaginationDTO;
import com.cloudinwind.selfmanage.dto.QuestionDTO;
import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.entity.UserAccount;
import com.cloudinwind.selfmanage.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wadao
 * @version 2.0
 * @date 2020/5/1 16:17
 * @site niter.cn
 */

@Controller
public class ForumIndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private HotTagCache hotTagCache;

    @Autowired
    private LoginUserCache loginUserCache;

    @Value("${site.main.index}")
    private int indexId;

    @GetMapping(value = {"/forum"})
    public String forum(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "15")Integer size,
                        @RequestParam(name = "column", required = false) Integer column2,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "tag", required = false) String tag,
                        @RequestParam(name = "sort", required = false) String sort) {
        // 判断用户之前是否已经登录
        UserDTO loginuser = (UserDTO) request.getAttribute("loginUser");
        System.out.println("loginUser:");
        UserAccount userAccount =null;
        if(loginuser!=null){
            userAccount = new UserAccount();
            BeanUtils.copyProperties(loginuser,userAccount);
            userAccount.setUserId(loginuser.getId());
            System.out.println("loginUser:"+loginuser.toString());
        }
        // 查询文章
        // 查询置顶帖 其中查询条件为 status=2 或 status=3
        List<QuestionDTO> topQuestions = questionService.listTopwithColumn(search, tag, sort,column2);
        // 查询普通贴 查询条件为 status=0(普通帖) 或 status=1(加精贴)
        PaginationDTO pagination = questionService.listwithColumn(search, tag, sort, page,size,column2,userAccount);
        List<String> tags = hotTagCache.getHots();
        List<User> loginUsers = loginUserCache.getLoginUsers();
        model.addAttribute("loginUsers", loginUsers);
        model.addAttribute("pagination",pagination);
        model.addAttribute("search", search);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", tags);
        model.addAttribute("sort", sort);
        model.addAttribute("column", column2);
        model.addAttribute("topQuestions", topQuestions);
        model.addAttribute("navtype", "communitynav");
        return "index";
    }

    @GetMapping(value = {"/"})
    public String index(HttpServletRequest request,
                        Model model) {
     if(indexId==2) return "redirect:/home";
      else  return "redirect:/forum";
    }



}
