package com.cloudinwind.selfmanage.service;

import com.cloudinwind.selfmanage.dto.PaginationDTO;
import com.cloudinwind.selfmanage.dto.ResultDTO;
import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.dto.UserQueryDTO;
import com.cloudinwind.selfmanage.entity.*;
import com.cloudinwind.selfmanage.exception.CustomizeErrorCode;
import com.cloudinwind.selfmanage.mapper.UserAccountMapper;
import com.cloudinwind.selfmanage.mapper.UserExtMapper;
import com.cloudinwind.selfmanage.mapper.UserInfoMapper;
import com.cloudinwind.selfmanage.mapper.UserMapper;
import com.cloudinwind.selfmanage.util.TokenUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private Environment env;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserExtMapper userExtMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private TokenUtils tokenUtils;
    @Value("${site.password.salt}")
    private String salt;

    public User selectUserByExample(UserExample userExample){
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size()!=0) return users.get(0);
        return null;
    }

    public ResultDTO repass(Long user_id, String nowpass, String pass){
        User user = userMapper.selectByPrimaryKey(user_id);
        if((StringUtils.isBlank(nowpass)&&StringUtils.isBlank(user.getPassword()))||DigestUtils.sha256Hex(nowpass+salt).equals(user.getPassword())){
            user.setPassword(DigestUtils.sha256Hex(pass+salt));
            int i = userMapper.updateByPrimaryKeySelective(user);
            if(i>0) return ResultDTO.okOf("????????????");
        }

        return ResultDTO.errorOf("??????????????????");
    }

    public User createOrUpdate(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        //User user2 = new User();
        //User dbUser = userMapper.findByAccountId(user.getAccountId());
       if (users.size() == 0) {
            // ??????
           if(user.getName()==null|| StringUtils.isBlank(user.getName()))
            user.setName(getUserName("Github"));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            userMapper.insert(user);
           UserExample userExample2 = new UserExample();
           userExample2.createCriteria()
                   .andAccountIdEqualTo(user.getAccountId());
           List<User> users2 = userMapper.selectByExample(userExample2);
           if(users2.size() != 0){//??????user????????????
               User dbUser = users2.get(0);
               BeanUtils.copyProperties(dbUser,user);
               initUserTable(dbUser,new UserInfo());
           }
        } else {
            //??????
            User dbUser = users.get(0);
            User updateUser = new User();
           BeanUtils.copyProperties(dbUser,user);
           if(dbUser.getName()==null&&(user.getName()==null|| StringUtils.isBlank(user.getName())))//??????????????????????????????
               updateUser.setName(getUserName("Github"));
          /* if(user.getName()!=null)//????????????
               updateUser.setName(user.getName());*/
            updateUser.setGmtModified(System.currentTimeMillis());
            //updateUser.setAvatarUrl(user.getAvatarUrl());
           // updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }

       return user;
    }

    public int createOrUpdateWeibo(User user, UserDTO loginuser, UserInfo userInfo) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andWeiboAccountIdEqualTo(user.getWeiboAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        User updateUser = new User();
        if (users.size() == 0) {  // ??????
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            if(user.getName()==null|| StringUtils.isBlank(user.getName()))
                user.setName(getUserName("??????"));
            if (loginuser == null) {//??????
                userMapper.insert(user);
                UserExample userExample2 = new UserExample();
                userExample2.createCriteria()
                        .andWeiboAccountIdEqualTo(user.getWeiboAccountId());
                List<User> users2 = userMapper.selectByExample(userExample2);
                if(users2.size() != 0){//??????user????????????
                    User dbUser = users2.get(0);
                    initUserTable(dbUser,userInfo);
                   /* userInfo.setUserId(dbUser.getId());
                    userInfoMapper.insert(userInfo);*/
                }
                return 1;
            }
            if (loginuser != null) {//??????????????????
                updateUser.setWeiboAccountId(user.getWeiboAccountId());
                updateUserInfo(user,updateUser,loginuser,userInfo);
                return 2;
            }



            // user.setName(getUserName("Github"));

        } else {
            //????????????
            User dbUser = users.get(0);
            if(dbUser.getName()==null&&(user.getName()==null|| StringUtils.isBlank(user.getName())))//??????????????????????????????
                updateUser.setName(getUserName("??????"));
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setToken(user.getToken());
            //updateUser.setAvatarUrl(user.getAvatarUrl());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
            return 3;
        }
        return 0;
    }


    public int createOrUpdateBaidu(User user, UserDTO loginuser, UserInfo userInfo) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andBaiduAccountIdEqualTo(user.getBaiduAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        User updateUser = new User();
        if (users.size() == 0) {  // ??????

            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            if(user.getName()==null|| StringUtils.isBlank(user.getName()))
                user.setName(getUserName("Baidu"));
            if (loginuser == null) {//??????
                userMapper.insert(user);
                UserExample userExample2 = new UserExample();
                userExample2.createCriteria()
                        .andBaiduAccountIdEqualTo(user.getBaiduAccountId());
                List<User> users2 = userMapper.selectByExample(userExample2);
                if(users2.size() != 0){//??????user????????????
                    User dbUser = users2.get(0);
                    initUserTable(dbUser,userInfo);
                   /* userInfo.setUserId(dbUser.getId());
                    userInfoMapper.insert(userInfo);*/
                }
                return 1;
              //  userMapper.insert(user);

            }
            if (loginuser != null) {//??????????????????
                updateUser.setBaiduAccountId(user.getBaiduAccountId());
                updateUserInfo(user,updateUser,loginuser,userInfo);
                return 2;
            }

        } else {
            //????????????
            User dbUser = users.get(0);
            if(dbUser.getName()==null&&(user.getName()==null|| StringUtils.isBlank(user.getName())))//??????????????????????????????
                updateUser.setName(getUserName("Baidu"));
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setToken(user.getToken());
           // updateUser.setAvatarUrl(user.getAvatarUrl());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
            return 3;
        }
        return 0;
    }

    public void createNewBaidu(User user, UserInfo userInfo) {
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        if(user.getName()==null|| StringUtils.isBlank(user.getName()))
            user.setName(getUserName("Baidu"));
        userMapper.insert(user);

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andBaiduAccountIdEqualTo(user.getBaiduAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() != 0){
            User dbUser = users.get(0);
            userInfo.setUserId(dbUser.getId());
            userInfoMapper.insert(userInfo);
        }


    }

    public String getUserName(String authorizeSize) {
        String str = RandomStringUtils.random(5,
                "abcdefghijklmnopqrstuvwxyz1234567890");
        String name = authorizeSize+"??????_"+str;
        return name;
    }

    public User selectUserByUserId(String userId) {
        Long id = Long.parseLong(userId);
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    public User selectUserByUserId(Long userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        return user;
    }

    public Object updateUserMailById(String userId,String mail) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andEmailEqualTo(mail);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() != 0){
            return ResultDTO.errorOf(CustomizeErrorCode.SUBMIT_MAIL_FAILED);
        }
        User updateUser = new User();
        updateUser.setEmail(mail);
        UserExample example = new UserExample();
        example.createCriteria()
                .andIdEqualTo(Long.parseLong(userId));
        try{
        userMapper.updateByExampleSelective(updateUser, example);
            return ResultDTO.okOf("????????????/?????????????????????");
        }catch (Exception e){
            return ResultDTO.errorOf(CustomizeErrorCode.SUBMIT_MAIL_FAILED);
        }

    }

    public int updateAvatarById(Long userId,String url){
        User user = userMapper.selectByPrimaryKey(userId);
        user.setAvatarUrl(url);
        return userMapper.updateByPrimaryKey(user);
    }

    public int updateUsernameById(Long userId,String username){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user.getName().equals(username)) return 1;
        else user.setName(username);
        return userMapper.updateByPrimaryKey(user);
    }

    public UserDTO getUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        UserAccount userAccount = userAccountService.selectUserAccountByUserId(user.getId());
        userDTO.setGroupId(userAccount.getGroupId());
        userDTO.setVipRank(userAccount.getVipRank());
        return userDTO;
    }

    public UserDTO getUserDTO(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        UserAccount userAccount = userAccountService.selectUserAccountByUserId(user.getId());
        userDTO.setGroupId(userAccount.getGroupId());
        userDTO.setVipRank(userAccount.getVipRank());
        userDTO.setGroupStr(env.getProperty("user.group.r"+userAccount.getGroupId()));
        return userDTO;
    }

    // ?????????????????? ???????????????
    public Object registerOrLoginWithMail(String mail,String password) {

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andEmailEqualTo(mail);
        List<User> users = userMapper.selectByExample(userExample);
        User updateUser = new User();
        if(password!=null)
            updateUser.setPassword(DigestUtils.sha256Hex(password+salt));
        // ???????????????????????????????????????, ?????????
        if(users.size() != 0){//??????
            User dbUser = users.get(0);
            UserDTO userDTO = getUserDTO(dbUser);
            if(dbUser.getName()==null|| StringUtils.isBlank(dbUser.getName()))//??????????????????????????????
                updateUser.setName(getUserName("??????"));
            updateUser.setGmtModified(System.currentTimeMillis());

            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
            return ResultDTO.okOf(tokenUtils.getToken(userDTO));
        }else {
            //??????
                updateUser.setEmail(mail);
                updateUser.setName(getUserName("??????"));
                updateUser.setAvatarUrl("/images/avatar/"+(int)(Math.random()*11)+".jpg");
                updateUser.setGmtCreate(System.currentTimeMillis());
                updateUser.setGmtModified(updateUser.getGmtCreate());
                updateUser.setCreateTime(new Date());
                updateUser.setUpdateTime(new Date());
                userMapper.insert(updateUser);
                UserExample example = new UserExample();
                example.createCriteria()
                        .andEmailEqualTo(mail);
                List<User> insertUsers = userMapper.selectByExample(example);
                //System.out.println("size:"+insertUsers.size());
                if(insertUsers.size() != 0){
                    User insertUser = insertUsers.get(0);
                    initUserTable(insertUser,new UserInfo());
                    UserDTO userDTO = getUserDTO(insertUser);
                    return ResultDTO.okOf(tokenUtils.getToken(userDTO));
                }

        }

        return ResultDTO.errorOf("????????????");


    }

    public Object updateUserPhoneById(Long userId, String phone) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andPhoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() != 0){
            return ResultDTO.errorOf(CustomizeErrorCode.SUBMIT_PHONE_FAILED);
        }
        User updateUser = new User();
        updateUser.setPhone(phone);
        UserExample example = new UserExample();
        example.createCriteria()
                .andIdEqualTo(userId);
        try{
            userMapper.updateByExampleSelective(updateUser, example);
            return ResultDTO.okOf("????????????/?????????????????????");
        }catch (Exception e){
            return ResultDTO.errorOf(CustomizeErrorCode.SUBMIT_PHONE_FAILED);
        }
    }

    public Object registerOrLoginWithPhone(String phone,String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andPhoneEqualTo(phone);
        List<User> users = userMapper.selectByExample(userExample);
        User updateUser = new User();
        if(password!=null)
            updateUser.setPassword(DigestUtils.sha256Hex(password+salt));
        if(users.size() != 0){//??????
            User dbUser = users.get(0);
            UserDTO userDTO = getUserDTO(dbUser);
            if(dbUser.getName()==null|| StringUtils.isBlank(dbUser.getName()))//??????????????????????????????
                updateUser.setName(getUserName("??????"));

            updateUser.setGmtModified(System.currentTimeMillis());

            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
            return ResultDTO.okOf(tokenUtils.getToken(userDTO));
        }else {
            //??????
            updateUser.setPhone(phone);
            updateUser.setName(getUserName("??????"));
            updateUser.setAvatarUrl("/images/avatar/"+(int)(Math.random()*11)+".jpg");
            updateUser.setGmtCreate(System.currentTimeMillis());
            updateUser.setGmtModified(updateUser.getGmtCreate());
            updateUser.setCreateTime(new Date());
            updateUser.setUpdateTime(new Date());
            userMapper.insert(updateUser);
            UserExample example = new UserExample();
            example.createCriteria()
                    .andPhoneEqualTo(phone);
            List<User> insertUsers = userMapper.selectByExample(example);
            User insertUser = insertUsers.get(0);
            initUserTable(insertUser,new UserInfo());
            UserDTO userDTO = getUserDTO(insertUser);
            return ResultDTO.okOf(tokenUtils.getToken(userDTO));
        }

    }

    public int createOrUpdateQq(User user, UserDTO loginuser, UserInfo userInfo) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andQqAccountIdEqualTo(user.getQqAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        User updateUser = new User();
        if (users.size() == 0) {  // ??????
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            if(user.getName()==null|| StringUtils.isBlank(user.getName()))
                user.setName(getUserName("QQ"));
            if (loginuser == null) {//??????
                userMapper.insert(user);
                UserExample userExample2 = new UserExample();
                userExample2.createCriteria()
                        .andQqAccountIdEqualTo(user.getQqAccountId());
                List<User> users2 = userMapper.selectByExample(userExample2);
                if(users2.size() != 0){//??????user????????????
                    User dbUser = users2.get(0);
                    initUserTable(dbUser,userInfo);
                   /* userInfo.setUserId(dbUser.getId());
                    userInfoMapper.insert(userInfo);
                    UserAccount userAccount = new UserAccount();
                    userAccount = initUserAccount(userAccount);
                    userAccount.setUserId(dbUser.getId());
                    userAccount.setGroupId(1);
                    userAccount.setVipRank(0);
                    userAccount.setScore(0);
                    userAccount.setScore1(0);
                    userAccount.setScore2(0);
                    userAccount.setScore3(0);
                    userAccountMapper.insert(userAccount);*/

                }
                return 1;
            }
            if (loginuser != null) {//??????????????????
                updateUser.setQqAccountId(user.getQqAccountId());
                updateUserInfo(user,updateUser,loginuser,userInfo);
              /*  updateUser.setGmtModified(System.currentTimeMillis());
                updateUser.setToken(user.getToken());
                updateUser.setAvatarUrl(user.getAvatarUrl());
                UserExample example = new UserExample();
                example.createCriteria()
                        .andIdEqualTo(loginuser.getId());
                userMapper.updateByExampleSelective(updateUser, example);

                UserInfoExample userInfoExample = new UserInfoExample();
                userInfoExample.createCriteria()
                        .andUserIdEqualTo(loginuser.getId());
                List<UserInfo> dbUserInfos = userInfoMapper.selectByExample(userInfoExample);
                if(dbUserInfos.size() == 0){//??????????????????
                    userInfo.setUserId(loginuser.getId());
                    userInfoMapper.insert(userInfo);
                }
                else{//??????????????????
                    UserInfoExample userInfEexample = new UserInfoExample();
                    userInfEexample.createCriteria()
                            .andUserIdEqualTo(loginuser.getId());
                    userInfoMapper.updateByExampleSelective(userInfo, userInfEexample);
                }*/
                return 2;
            }



            // user.setName(getUserName("Github"));

        } else {
            //????????????
            User dbUser = users.get(0);
            if(dbUser.getName()==null&&(user.getName()==null|| StringUtils.isBlank(user.getName())))//??????????????????????????????
                updateUser.setName(getUserName("QQ"));
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setToken(user.getToken());
            //updateUser.setAvatarUrl(user.getAvatarUrl());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
            return 3;
        }
        return 0;
    }

    public UserAccount initUserAccount(UserAccount userAccount){
        userAccount.setGroupId(1);
        userAccount.setVipRank(0);
        userAccount.setScore(0);
        userAccount.setScore1(0);
        userAccount.setScore2(0);
        userAccount.setScore3(0);
        userAccount.setCreateTime(new Date());
        userAccount.setUpdateTime(new Date());
        return userAccount;
    }

    public void initUserTable(User user, UserInfo userInfo){
       // UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfoMapper.insert(userInfo);
        UserAccount userAccount = new UserAccount();
        userAccount = initUserAccount(userAccount);
        userAccount.setUserId(user.getId());
        userAccount.setCreateTime(new Date());
        userAccount.setUpdateTime(new Date());
        userAccountMapper.insert(userAccount);
        userInfo=null;
        userAccount=null;
    }

    public void updateUserInfo(User user, User updateUser, UserDTO loginuser, UserInfo userInfo){
        updateUser.setGmtModified(System.currentTimeMillis());
        updateUser.setToken(user.getToken());
        //updateUser.setAvatarUrl(user.getAvatarUrl());
        UserExample example = new UserExample();
        example.createCriteria()
                .andIdEqualTo(loginuser.getId());
        userMapper.updateByExampleSelective(updateUser, example);

        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria()
                .andUserIdEqualTo(loginuser.getId());
        List<UserInfo> dbUserInfos = userInfoMapper.selectByExample(userInfoExample);
        if(dbUserInfos.size() == 0){//??????????????????
            userInfo.setUserId(loginuser.getId());
            userInfoMapper.insert(userInfo);
        }
        else{//??????????????????
            UserInfoExample userInfEexample = new UserInfoExample();
            userInfEexample.createCriteria()
                    .andUserIdEqualTo(loginuser.getId());
            userInfoMapper.updateByExampleSelective(userInfo, userInfEexample);
        }

    }

    public Object login(Integer type, String name, String password) {
        UserExample userExample = new UserExample();
        System.out.println("pw:"+password);
        System.out.println("salt:"+salt);
        System.out.println("pw+salt:"+DigestUtils.sha256Hex(password+salt));
        if(type==1){//???????????????
            userExample.createCriteria().andPhoneEqualTo(name).andPasswordEqualTo(DigestUtils.sha256Hex(password+salt));
        }else if(type==2){//????????????
            userExample.createCriteria().andEmailEqualTo(name).andPasswordEqualTo(DigestUtils.sha256Hex(password+salt));
        }else {
            return ResultDTO.errorOf("????????????????????????");
        }
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size()!=0){
            ResultDTO resultDTO = ResultDTO.okOf("????????????");
            resultDTO.setData(tokenUtils.getToken(getUserDTO(users.get(0))));
            return resultDTO;
        }
        else return ResultDTO.errorOf(CustomizeErrorCode.LOGIN_FAILED);
    }

    public Object register(Integer type, String name, String password) {
        if(1==type){
            return registerOrLoginWithPhone(name,password);
        }else if(2==type){
            //updateUser.setEmail(name);
           // updateUser.setName(getUserName("??????"));
            return registerOrLoginWithMail(name,password);
        }
       return ResultDTO.errorOf("?????????????????????????????????");
    }

    public PaginationDTO list(UserQueryDTO userQueryDTO) {
        Integer totalPage;
        Integer totalCount = userExtMapper.count(userQueryDTO);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if(userQueryDTO.getId()!=null&&(userQueryDTO.getId().longValue()!=0L))
            criteria.andIdEqualTo(userQueryDTO.getId());
        if(StringUtils.isNotBlank(userQueryDTO.getName()))
            criteria.andNameEqualTo(userQueryDTO.getName());
        if(StringUtils.isNotBlank(userQueryDTO.getPhone()))
            criteria.andPhoneEqualTo(userQueryDTO.getPhone());
        if(StringUtils.isNotBlank(userQueryDTO.getEmail()))
            criteria.andEmailEqualTo(userQueryDTO.getEmail());


        if (totalCount % userQueryDTO.getSize() == 0) {
            totalPage = totalCount / userQueryDTO.getSize();
        } else {
            totalPage = totalCount / userQueryDTO.getSize() + 1;
        }

        if (userQueryDTO.getPage() > totalPage) {
            userQueryDTO.setPage(totalPage);
        }

        Integer offset = userQueryDTO.getPage() < 1 ? 0 : userQueryDTO.getSize() * (userQueryDTO.getPage() - 1);
        userQueryDTO.setOffset(offset);
        userExample.setOrderByClause(userQueryDTO.getSort()+" "+userQueryDTO.getOrder());

        List<User> users = userMapper.selectByExampleWithRowbounds(userExample,new RowBounds(userQueryDTO.getSize()*(userQueryDTO.getPage()-1), userQueryDTO.getSize()));
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setTotalCount(totalCount);
        if (users.size() == 0) {
            paginationDTO.setPage(0);
            paginationDTO.setTotalPage(0);
            paginationDTO.setData(new ArrayList<>());
            return paginationDTO;
        }
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(getUserDTO(user));
        }
        paginationDTO.setData(userDTOs);
        paginationDTO.setPagination(totalPage,userQueryDTO.getPage());
        return paginationDTO;
        //return userDTOs;
    }
}
