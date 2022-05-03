package com.cloudinwind.selfmanage.service.admin;

import com.cloudinwind.selfmanage.dto.ResultDTO;
import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.entity.UserAccount;
import com.cloudinwind.selfmanage.entity.UserExample;
import com.cloudinwind.selfmanage.exception.CustomizeErrorCode;
import com.cloudinwind.selfmanage.mapper.UserMapper;
import com.cloudinwind.selfmanage.service.UserAccountService;
import com.cloudinwind.selfmanage.util.TokenUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminOperateService {
    @Autowired
    private TokenUtils tokenUtils;
    @Value("${site.password.salt}")
    private String salt;


    @Resource
    UserMapper userMapper;

    @Autowired
    private UserAccountService userAccountService;

    public Object login(Integer type, String name, String password) {
        UserExample userExample = new UserExample();
        System.out.println("pw:"+password);
        System.out.println("salt:"+salt);
        System.out.println("pw+salt:"+ DigestUtils.sha256Hex(password+salt));
        if(type==1){//手机号登录
            userExample.createCriteria().andPhoneEqualTo(name).andPasswordEqualTo(DigestUtils.sha256Hex(password+salt));
        }else if(type==2){//邮箱登录
            userExample.createCriteria().andEmailEqualTo(name).andPasswordEqualTo(DigestUtils.sha256Hex(password+salt));
        }else {
            return ResultDTO.errorOf("不支持此登录类型");
        }
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size()!=0){

            UserAccount userAccount = userAccountService.selectUserAccountByUserId(users.get(0).getId());

            // 判断用户所属于分组
            int groupId = userAccount.getGroupId();
            if (groupId == 19 || groupId==20 || groupId==21 ){
                ResultDTO resultDTO = ResultDTO.okOf("登录成功");
                resultDTO.setData(getUserDTO(users.get(0)));
                return resultDTO;
            }
        }
        return ResultDTO.errorOf(CustomizeErrorCode.LOGIN_FAILED);
    }

    public UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        UserAccount userAccount = userAccountService.selectUserAccountByUserId(user.getId());
        userDTO.setGroupId(userAccount.getGroupId());
        userDTO.setVipRank(userAccount.getVipRank());
        return userDTO;
    }
}
