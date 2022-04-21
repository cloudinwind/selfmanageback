package com.cloudinwind.selfmanage.controller;

import com.cloudinwind.selfmanage.cache.AppUserCache;
import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.entity.UserAccount;
import com.cloudinwind.selfmanage.exception.CustomizeErrorCode;
import com.cloudinwind.selfmanage.exception.CustomizeException;
import com.cloudinwind.selfmanage.service.UserAccountService;
import com.cloudinwind.selfmanage.service.UserService;
import com.cloudinwind.selfmanage.util.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SSOController {
    @Value("${vaptcha.vid}")
    private String vaptcha_vid; // 手势验证码
    @Value("${sms.enable}")
    private Integer smsEnable;

    @Autowired
    private AppUserCache appUserCache;  // 用户缓存
    @Autowired
    private UserService userService;    // 用户缓存
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private TokenUtils tokenUtils;

    // 登录、注册 页面跳转
    @RequestMapping("/sso/{action}")
    public String aouth(HttpServletRequest request,
                        HttpServletResponse response,
                        @PathVariable(name = "action") String action,
                        Model model) {
        // 判断用户是否已经登录
        UserDTO user = (UserDTO)request.getAttribute("loginUser");
        if(user != null) {
            return "redirect:/forum";
        }
        model.addAttribute("vaptcha_vid", vaptcha_vid);
        if("login".equals(action)){
            model.addAttribute("initOssType", 3);
            model.addAttribute("section", "login");
            model.addAttribute("sectionName", "登录");
           // return "/user/login";
        }
        else if("register".equals(action)){
            model.addAttribute("initOssType", 2);
            model.addAttribute("section", "register");
            model.addAttribute("sectionName", "注册");
          //  return "/user/reg";
        }
        else if("reset".equals(action)){
            model.addAttribute("initOssType", 2);
            model.addAttribute("section", "register");
            model.addAttribute("sectionName", "重置密码");
            //  return "/user/reg";
        }
        else {
            return "redirect:/forum";
        }
        model.addAttribute("smsEnable", smsEnable);
        return "user/sso";
    }

    @RequestMapping("/sso/appConfirm")
    public String qrcodeStr(HttpServletRequest request,
                            HttpServletResponse response,
                            @RequestParam(name = "qrcodeStr") String qrcodeStr,
                            Model model) {
        UserDTO user = (UserDTO)request.getAttribute("loginUser");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }
            model.addAttribute("qrcodeStr", qrcodeStr);
           // System.out.println("qrcodeStr:"+qrcodeStr);
            return "user/appConfirm";
    }

    @PostMapping("/sso/putQrcodeStr")
    @ResponseBody
    public Map<String,Object> putQrcodeStr(HttpServletRequest request,
                                           @RequestParam(name = "qrcodeStr") String qrcodeStr) {

        UserDTO loginUser = (UserDTO)request.getAttribute("loginUser");
        if (loginUser == null) {
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN);
        }

        Map<String,Object> map  = new HashMap<>();
        User user=userService.selectUserByUserId(loginUser.getId());
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        UserAccount userAccount = userAccountService.selectUserAccountByUserId(user.getId());
        userDTO.setGroupId(userAccount.getGroupId());
        userDTO.setVipRank(userAccount.getVipRank());
       // String token = user.getToken();
        //System.out.println("token:"+token);
        appUserCache.put(qrcodeStr,tokenUtils.getToken(userDTO));
       // System.out.println("cachetoken:"+appUserCache.get(qrcodeStr));
        map.put("success",1);
        return map;

    }

    @GetMapping("/sso/appConfirmResult")
    @ResponseBody
    public Map<String,Object> appConfirmResult(HttpServletRequest request,
                                               @RequestParam(name = "qrcodeStr") String qrcodeStr) {

         //System.out.println("qrcodeStr2:"+qrcodeStr);
        Map<String,Object> map  = new HashMap<>();
        String token = appUserCache.get(qrcodeStr);
        if(token==null||"".equals(token))
                  map.put("success",0);
        else{
            map.put("success",1);
            map.put("token",token);
           // System.out.println("token2:"+token);
        }
        return map;

    }





}
