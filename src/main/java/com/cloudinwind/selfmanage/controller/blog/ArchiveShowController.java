package com.cloudinwind.selfmanage.controller.blog;

import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.service.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    // 时间线归档
    @GetMapping("/archives")
    public String archives(Model model, HttpServletRequest request) {
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        model.addAttribute("archiveMap", blogService.archiveUserBlog(userId));
        model.addAttribute("blogCount", blogService.countUserBlog(userId));
        return "blog/userArchives";
    }
}
