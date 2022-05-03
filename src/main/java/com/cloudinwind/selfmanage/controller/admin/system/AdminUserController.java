package com.cloudinwind.selfmanage.controller.admin.system;

import com.cloudinwind.selfmanage.constant.PageConstant;
import com.cloudinwind.selfmanage.controller.time.BaseController;
import com.cloudinwind.selfmanage.dto.UserQueryDTO;
import com.cloudinwind.selfmanage.entity.*;
import com.cloudinwind.selfmanage.entity.admin.AdminAnnounce;
import com.cloudinwind.selfmanage.entity.time.ReturnBean;
import com.cloudinwind.selfmanage.mapper.UserAccountMapper;
import com.cloudinwind.selfmanage.mapper.UserExtMapper;
import com.cloudinwind.selfmanage.mapper.UserInfoMapper;
import com.cloudinwind.selfmanage.mapper.UserMapper;
import com.cloudinwind.selfmanage.vo.admin.UserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("admin/system/user")
public class AdminUserController extends BaseController {

    @Resource
    UserExtMapper userExtMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    UserAccountMapper userAccountMapper;

    @Resource
    UserInfoMapper userInfoMapper;

    @Value("${site.password.salt}")
    private String salt;

    // 问题页面 分页查询
    @RequestMapping("selectByPage")
    public ReturnBean<List<AdminAnnounce>> selectByPage(Integer page, Integer limit, UserVo userVo) {

        if (page == null){
            page = PageConstant.page;
            limit= PageConstant.limit;
        }
        // 分页查询
        List<UserVo> userVos = userExtMapper.selectUserAll(page, limit, userVo);
        List<UserVo> userVos2 = new ArrayList<>();
        // 设置角色
        for (UserVo userVo1: userVos){
            int groupId = userVo1.getGroupId();
            if (groupId<18) userVo1.setGroupStr("普通用户");
            else if (groupId==18) userVo1.setGroupStr("VIP用户");
            else if (groupId== 19) userVo1.setGroupStr("管理员");
            else if (groupId == 20) userVo1.setGroupStr("站长");
            else userVo1.setGroupStr("副站长");

            userVos2.add(userVo1);
        }


        // 查询总数量
        UserQueryDTO userQueryDTO = new UserQueryDTO();
        Integer countUser = userExtMapper.count(userQueryDTO);

        return success(userVos2, countUser.longValue());

    }

    /**
     * 插入数据
     * @param
     * @return
     */
    @PostMapping("insert")
    public ReturnBean insert(@RequestBody UserVo userVo) {

        // 插入user表
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setPassword(DigestUtils.sha256Hex(userVo.getPassword()+salt));
        userMapper.insert(user);


        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(userVo.getEmail());
        List<User> users = userMapper.selectByExample(userExample);
        // 插入关联表 user_account
        UserAccount userAccount = new UserAccount();
        userAccount.setCreateTime(new Date());
        userAccount.setUpdateTime(new Date());
        userAccount.setUserId(users.get(0).getId());
        userAccount.setGroupId(userVo.getGroupId());
        userAccountMapper.insert(userAccount);

        // 插入关联表 user_info
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(users.get(0).getId());
        userInfoMapper.insert(userInfo);


        // 更新userVo
        userVo.setId(users.get(0).getId());

        return success(userVo);
    }


    /**
     * 修改数据

     */
    @PutMapping("update")
    public ReturnBean update(@RequestBody UserVo userVo) {
        //获取当前登录用户
        //设置修改时间

        // 更新 user表
        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);

        // 更新关联表 user_account
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userVo.getId());
        userAccount.setGroupId(userVo.getGroupId());
        userAccount.setUpdateTime(new Date());

        UserAccountExample userAccountExample = new UserAccountExample();
        userAccountExample.createCriteria().andUserIdEqualTo(userVo.getId());
        userAccountMapper.updateByExampleSelective(userAccount, userAccountExample);

        return success(userVo);
    }

    // 重置密码
    @PutMapping("resetPassword")
    public ReturnBean resetPassword(@RequestBody UserVo userVo) {

        User user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setPassword(DigestUtils.sha256Hex(userVo.getPassword()+salt));
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        return success(userVo);
    }

    /**
     * 删除数据
     * 根据问题id批量删除问题
     * @param idList 主键结合
     * @return 删除结果
     */
    @RequestMapping("deleteByIdList")
    public ReturnBean delete(@RequestParam(value = "idList[]", required = false) List<Long> idList) {
        System.out.println("idList:"+idList.size());

        for (Long l: idList){
            userMapper.deleteByPrimaryKey(l);
            UserAccountExample userAccountExample = new UserAccountExample();
            userAccountExample.createCriteria().andUserIdEqualTo(l);
            userAccountMapper.deleteByExample(userAccountExample);

//            UserInfoExample userInfoExample = new UserInfoExample();
//            userAccountExample.createCriteria().andUserIdEqualTo(l);
//            userInfoMapper.deleteByExample(userInfoExample);
        }

        return success(idList);
    }


}
