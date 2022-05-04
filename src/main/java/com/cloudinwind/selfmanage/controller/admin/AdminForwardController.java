package com.cloudinwind.selfmanage.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.entity.UserAccount;
import com.cloudinwind.selfmanage.entity.UserAccountExample;
import com.cloudinwind.selfmanage.entity.admin.AdminAnnounce;
import com.cloudinwind.selfmanage.entity.forum.AdExample;
import com.cloudinwind.selfmanage.entity.time.Label;
import com.cloudinwind.selfmanage.entity.time.LayUiTree;
import com.cloudinwind.selfmanage.entity.time.Task;
import com.cloudinwind.selfmanage.entity.time.UserTask;
import com.cloudinwind.selfmanage.mapper.AdMapper;
import com.cloudinwind.selfmanage.mapper.UserAccountMapper;
import com.cloudinwind.selfmanage.mapper.UserMapper;
import com.cloudinwind.selfmanage.service.UserAccountService;
import com.cloudinwind.selfmanage.service.admin.AdminAnnounceService;
import com.cloudinwind.selfmanage.service.admin.MenuService;
import com.cloudinwind.selfmanage.service.time.LabelService;
import com.cloudinwind.selfmanage.service.time.TaskService;
import com.cloudinwind.selfmanage.service.time.UserTaskService;
import com.cloudinwind.selfmanage.util.DateUtils;
import com.cloudinwind.selfmanage.vo.admin.UserAccountVo;
import com.sun.jmx.snmp.tasks.TaskServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminForwardController {

    @Resource
    UserMapper userMapper;

    @Resource
    MenuService menuService;

    @Resource
    UserAccountService userAccountService;

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    AdminAnnounceService adminAnnounceService;

    @Resource
    AdMapper adMapper;

    @Resource
    TaskService taskService;

    @Resource
    LabelService labelService;

    @Resource
    UserTaskService userTaskService;

    @RequestMapping("toLogin")
    public String toLogin(){
        return "admin/login";
    }



    @RequestMapping("toHome")
    public ModelAndView toHome(@RequestParam("adminId")Integer adminId, HttpServletRequest request,
                         ModelAndView modelAndView){

        // 获取管理员信息
        User adminUser = userMapper.selectByPrimaryKey(adminId.longValue());
        System.out.println("adminUser:"+adminUser.toString());
        modelAndView.addObject("adminUser", adminUser);

        // 获取侧边菜单栏信息
        List<LayUiTree> menus = menuService.queryAllMenusByUserId(adminId);
        modelAndView.addObject("menus",menus);

        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping("system/overAllAnalysis")
    public ModelAndView overAllAnalysis(ModelAndView modelAndView){
        UserAccountVo userAccountVo = new UserAccountVo();

        int countUser = 0;
        // 查询所有用户数量
        List<UserAccountVo> allUserAccounts = userAccountService.getAllUserAccount(userAccountVo);
        countUser = allUserAccounts.get(0).getCountUser();
        modelAndView.addObject("userAllSum", countUser);

        // 查询管理员数量 group_id = 19, 20, 21
        UserAccountExample userAccountExample = new UserAccountExample();
        userAccountExample.createCriteria().andGroupIdGreaterThanOrEqualTo(19);
        List<UserAccount> userAccounts = userAccountMapper.selectByExample(userAccountExample);
        countUser = userAccounts.size();
        modelAndView.addObject("userAdmin", countUser);

        // 查询VIP数量
        userAccountExample = new UserAccountExample();
        userAccountExample.createCriteria().andGroupIdEqualTo(18);
        userAccounts = userAccountMapper.selectByExample(userAccountExample);
        countUser = userAccounts.size();
        modelAndView.addObject("userVIP", countUser);

        // 广告数量
        AdExample adExample = new AdExample();
        long adL = adMapper.countByExample(adExample);
        modelAndView.addObject("adCount", adL);

        // 系统公告
        QueryWrapper<AdminAnnounce> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        Page<AdminAnnounce> adminAnnouncePage = new Page<>(1, 10);
        Page<AdminAnnounce> announcePage = adminAnnounceService.page(adminAnnouncePage, queryWrapper);
        List<AdminAnnounce> adminAnnounces = announcePage.getRecords();
        modelAndView.addObject("adminAnnounces", adminAnnounces);

        modelAndView.setViewName("admin/systemAnalysis/totalAnalysis");
        return modelAndView;
    }


    @RequestMapping("system/toAdminAnnounceManage")
    public ModelAndView toAdminAnnounceManage(ModelAndView modelAndView){
        modelAndView.setViewName("admin/systemAnalysis/adminAnnounceManage");
        return modelAndView;
    }

    @RequestMapping("system/toAdminUserManage")
    public ModelAndView toAdminUserManage(ModelAndView modelAndView){
        modelAndView.setViewName("admin/systemAnalysis/userManage");
        return modelAndView;
    }

    @RequestMapping("task/toTaskManage")
    public ModelAndView toTaskManage(ModelAndView modelAndView){
        // 查询所有用户数量
        List<Task> tasks = taskService.list();
        modelAndView.addObject("countTask", tasks.size());
        // 查询所有标签数量
        List<Label> labels = labelService.list();
        modelAndView.addObject("countLabel", labels.size());
        System.out.println("label:");
        // 查询活跃用户
        QueryWrapper<UserTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("user_id");
        queryWrapper.between("create_time", DateUtils.getPastDate(6), DateUtils.getPastDate(0));

        List<UserTask> userTasks = userTaskService.list(queryWrapper);
        modelAndView.addObject("countUser", userTasks.size());
        System.out.println("countUser");
        // 查询时长

        modelAndView.setViewName("admin/time/timeManage");

        return modelAndView;

    }

    @RequestMapping("analysis/toMbtiQuestionManage")
    public ModelAndView toMbtiQuestionManage(ModelAndView modelAndView){
        modelAndView.setViewName("admin/analysis/mbtiQuestionManage");
        return modelAndView;
    }



}
