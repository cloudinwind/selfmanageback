package com.cloudinwind.selfmanage.controller.analysis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudinwind.selfmanage.controller.time.BaseController;
import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.entity.analysis.Mail2;
import com.cloudinwind.selfmanage.entity.analysis.UserMbtiresult;
import com.cloudinwind.selfmanage.entity.time.ReturnBean;
import com.cloudinwind.selfmanage.mapper.UserMapper;
import com.cloudinwind.selfmanage.service.analysis.UserMbtiresultService;
import com.cloudinwind.selfmanage.util.JavaMailUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * (UserMbtiresult)表控制层
 *
 * @author makejava
 * @since 2022-04-27 21:28:16
 */
@RestController
@RequestMapping("/analysis/mbtiResult")
public class UserMbtiresultController extends BaseController {

    @Resource
    UserMbtiresultService userMbtiresultService;

    @Resource
    UserMapper userMapper;

    // 批量插入
    @PostMapping("insertBatch")
    public ReturnBean insertBatch(@RequestBody Collection<UserMbtiresult> result,
                                  HttpServletRequest request){
        // 先判断该用户是否已经检测过, 直接删除MBTI检测结果表中和该用户相关的信息
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();
        QueryWrapper<UserMbtiresult> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        userMbtiresultService.remove(queryWrapper);

        // 将新的检测结果插入到结果表中
        boolean flag = userMbtiresultService.saveBatch(result);

        if (flag){
            return super.success("提交成功");
        } else{
            return fail("提交失败");
        }
    }

    @RequestMapping("sendToMail")
    public ReturnBean sendToMail(@RequestBody Mail2 mail,  HttpServletRequest request){
        // 先判断该用户是否有邮箱
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        User user = userMapper.selectByPrimaryKey(loginUser.getId());
        if (user.getEmail() == null){
            return fail(mail, "请先绑定邮箱");
        }


        JavaMailUtils.sendMailCharacterFile(user.getEmail(),"MBTI性格测试报告","MBTI性格测试",mail.getResult());

        return success(mail, "发送成功");
    }

//    // 查询 所有用户的最终测试结果
//    @RequestMapping("selectAllTesterResult")
//    public ReturnBean selectAllTesterResult(Integer page, Integer limit, Tester tester){
////        if (page == null){
////            page = Constants.page;
////            limit = Constants.limit;
////        }
////        if (tester!=null){
////            System.out.println("result:"+tester.toString());
////        }
////        List<ResultVo> resultVos = resultService.selectAllTesterResult(page, limit, tester);
////
////        return success(resultVos, resultService.count(tester));
//        return null;
//    }
//



//    /**
//     * 删除数据
//     * 根据问题id批量删除问题
//     * @param idList 主键结合
//     * @return 删除结果
//     */
//    @RequestMapping("deleteByIdList")
//    public ReturnBean delete(@RequestParam(value = "idList[]", required = false) List<Integer> idList) {
//        System.out.println("idList:"+idList.size());
//
//        // 先删除tester表中的用户
//        boolean delete = testerService.removeByIds(idList);
//        if (delete){
//            // 再删除result表中的数据
//            userMbtiresultService.removeByIds(idList);
//
//            return success(idList);
//        }
//        else return fail(idList);
//    }

}
