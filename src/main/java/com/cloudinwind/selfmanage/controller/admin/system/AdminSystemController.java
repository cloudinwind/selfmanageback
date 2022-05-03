package com.cloudinwind.selfmanage.controller.admin.system;

import com.cloudinwind.selfmanage.controller.time.BaseController;
import com.cloudinwind.selfmanage.entity.time.ReturnBean;
import com.cloudinwind.selfmanage.service.UserAccountService;
import com.cloudinwind.selfmanage.util.DateUtils;
import com.cloudinwind.selfmanage.vo.admin.UserAccountVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class AdminSystemController extends BaseController {

    @Resource
    UserAccountService userAccountService;

    @RequestMapping("system/adminUser")
    public ReturnBean adminUser(){
        UserAccountVo userAccountVo = new UserAccountVo();
        userAccountVo.setStartDateCondition(DateUtils.getPastDate(6));
        userAccountVo.setEndDateCondition(DateUtils.getPastDate(0));
        // 查询所有用户
        List<UserAccountVo> userAccountVos = userAccountService.getAllUserAccountGroupByTime(userAccountVo);

        // 查询VIP用户
        userAccountVo.setGroupId(18);
        List<UserAccountVo> userAccountVoVIPs = userAccountService.getAllUserAccountGroupByTime(userAccountVo);

        Map<Integer, Map<String, Integer>> mapHashMap= new HashMap<>();
        Map<String, Integer> mapAll = new HashMap<>();
        Map<String, Integer> mapVIP = new HashMap<>();

        // 初始化
        for (int i=0; i<=6; i++){
            mapAll.put(DateUtils.getPastDate(i), 0);
            mapVIP.put(DateUtils.getPastDate(i), 0);
        }

        for (UserAccountVo userAccountVo1: userAccountVos){
            mapAll.put(DateUtils.dateToStr(userAccountVo1.getCreateTime()), userAccountVo1.getCountUser());
        }

        for (UserAccountVo userAccountVo1: userAccountVoVIPs){
            mapVIP.put(DateUtils.dateToStr(userAccountVo1.getCreateTime()), userAccountVo1.getCountUser());
        }
        mapHashMap.put(0, mapAll);
        mapHashMap.put(1, mapVIP);
        return success(mapHashMap);

    }
}
