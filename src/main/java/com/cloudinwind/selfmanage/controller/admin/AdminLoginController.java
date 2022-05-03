package com.cloudinwind.selfmanage.controller.admin;

import com.cloudinwind.selfmanage.controller.time.BaseController;
import com.cloudinwind.selfmanage.dto.ResultDTO;
import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.entity.time.ReturnBean;
import com.cloudinwind.selfmanage.service.admin.AdminOperateService;
import com.cloudinwind.selfmanage.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminLoginController extends BaseController {

    @Autowired
    private AdminOperateService adminOperateService;

    @Autowired
    private CookieUtils cookieUtils;

    @RequestMapping("login")
    public ReturnBean adminLogin(@RequestBody User user,
                                 @RequestParam(required = false, defaultValue = "2", value = "type") Integer type,
                                 Model model){

        System.out.println(user.toString());
        //1为手机号，2为邮箱号
        ResultDTO resultDTO = (ResultDTO)adminOperateService.login(type,user.getEmail(),user.getPassword());

        System.out.println("resultDto: "+resultDTO.toString());
        if(200==resultDTO.getCode()){
            UserDTO userDTO = (UserDTO) resultDTO.getData();
            return success(userDTO, "成功登录");
        }
        return fail(resultDTO, "登陆失败");
    }
}
