package com.cloudinwind.selfmanage.controller.time;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 进行页面跳转
 */
@Controller
public class ForwardController {

    /**
     * 到任务管理界面
     * @return
     */
    @RequestMapping("task/toTaskManage/{userId}")
    public ModelAndView toTaskManage(@PathVariable("userId") Integer userId, ModelAndView modelAndView){
        modelAndView.addObject("timeUserId", userId);
        modelAndView.setViewName("time/taskManage");
        return modelAndView;
    }

    /**
     * 标签管理页面
     */
    @RequestMapping("task/toLabelManage/{userId}")
    public ModelAndView toLabelManage(@PathVariable("userId") Integer userId, ModelAndView modelAndView){
        System.out.println("toLabelManage:" + userId);
        modelAndView.addObject("timeUserId", userId);
        modelAndView.setViewName("time/labelManage");
        return modelAndView;
    }


    @RequestMapping("toTimeHome")
    public ModelAndView toHome(ModelAndView mv){
        //设置跳转页面
        mv.setViewName("time/home");
        return mv;
    }

    /**
     * 注销
     */
    @RequestMapping("logout")
    public ModelAndView logout(ModelAndView mv){
        //退回管理登录界面
        mv.setViewName("user/userLogin");
        return mv;
    }


}
