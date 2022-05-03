package com.cloudinwind.selfmanage.controller.time;

import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.service.time.TaskService;
import com.cloudinwind.selfmanage.vo.time.TaskVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 进行页面跳转
 */
@Controller
public class ForwardController {


    @Resource
    TaskService taskService;


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

    // 时间线页面
    @RequestMapping("/time/toTimeLine")
    public ModelAndView selectUserTask(@RequestParam(required = false, value = "nowDate")String nowDate,
                                     HttpServletRequest request, ModelAndView modelAndView)
    {

        TaskVo taskVo = new TaskVo();
        if (nowDate == null){
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            nowDate = sdf.format(date);
        }

        taskVo.setNowDate(nowDate);

        // 获取user
        UserDTO loginuser = (UserDTO) request.getAttribute("loginUser");
        taskVo.setUserId(loginuser.getId().intValue());
        // 按照用户id分页查找

        List<TaskVo> taskVoList = taskService.selectUserTask(taskVo);

        modelAndView.addObject("nowDate", nowDate);
        modelAndView.addObject("taskVos", taskVoList);

        modelAndView.setViewName("time/taskTimeLine");


        return modelAndView;


    }

    // 到任务分析页面 日分析
    @RequestMapping("/time/taskAnalysisDay")
    public ModelAndView taskAnalysisDay(@RequestParam(required = false, value = "nowDate")String nowDate,
                                     ModelAndView mv){

        if (nowDate == null)
        {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            nowDate = sdf.format(date);
        }
        System.out.println("nowDate:" + nowDate);
        mv.addObject("nowDate", nowDate);
        //设置跳转页面
        mv.setViewName("time/taskAnalysisDay");
        return mv;
    }

    // 到任务分析页面 周分析
    @RequestMapping("/time/taskAnalysisWeek")
    public ModelAndView taskAnalysisWeek(@RequestParam(required = false, value = "nowDate")String nowDate,
                                     ModelAndView mv){

        //设置跳转页面
        mv.setViewName("time/taskAnalysisWeek");
        return mv;
    }

    // 到任务分析页面 月分析
    @RequestMapping("/time/taskAnalysisMonth")
    public ModelAndView taskAnalysisMonth(@RequestParam(required = false, value = "nowDate")String nowDate,
                                         ModelAndView mv){

        //设置跳转页面
        mv.setViewName("time/taskAnalysisMonth");
        return mv;
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
