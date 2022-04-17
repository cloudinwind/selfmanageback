package com.cloudinwind.selfmanage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @GetMapping(value = {"/"})
    public String index(HttpServletRequest request,
                        Model model) {
        return "redirect:/forum";
    }

    @GetMapping(value = {"/index2"})
    public String index2(HttpServletRequest request,
                        Model model) {
        return "index2";
    }
}
