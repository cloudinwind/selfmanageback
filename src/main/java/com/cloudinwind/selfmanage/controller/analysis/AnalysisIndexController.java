package com.cloudinwind.selfmanage.controller.analysis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloudinwind.selfmanage.constant.PageConstant;
import com.cloudinwind.selfmanage.dto.UserDTO;
import com.cloudinwind.selfmanage.entity.analysis.UserMbtiresult;
import com.cloudinwind.selfmanage.mapper.UserMapper;
import com.cloudinwind.selfmanage.service.analysis.UserMbtiresultService;
import com.cloudinwind.selfmanage.vo.analysis.MbtiResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/analysis")
public class AnalysisIndexController {

    @Resource
    UserMbtiresultService userMbtiresultService;

    @Resource
    UserMapper userMapper;

    @RequestMapping()
    public String anlaysisSelf(){

        return "analysis/index";
    }

    // 进行MBTI测试
    @RequestMapping("/mbti")
    public String mbtiTest(Model model,  HttpServletRequest request){
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        // 判断该用户是否已经检测过
        QueryWrapper<UserMbtiresult> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        int count = userMbtiresultService.count(queryWrapper);
        if (count>0) {
            // 说明已经检测过
            // 查询检测结果
            List<MbtiResultVo> mbtiResultVos = userMbtiresultService.selectAllMbtiResult(PageConstant.page, PageConstant.limit, userMapper.selectByPrimaryKey(userId));
            MbtiResultVo mbtiResultVo = mbtiResultVos.get(0);

            mbtiResultVo = setMbtiResultVoResult(mbtiResultVo);
            model.addAttribute("mbtiResultVo", mbtiResultVo);
            // 返回到检测结果页面

            System.out.println(mbtiResultVo.getResult().toLowerCase());
//            return "analysis/mbtiResult";
            return "analysis/mbti/mbtiResult";
        }

        // 未检测过,直接检测
        return "analysis/mbtiTestQuestion";
    }

    // 跳转到测试结果页面
    @RequestMapping("/toMbtiResult")
    public String mbtiResult(Model model,  HttpServletRequest request){
        UserDTO loginUser = (UserDTO) request.getAttribute("loginUser");
        Long userId = loginUser.getId();

        List<MbtiResultVo> mbtiResultVos = userMbtiresultService.selectAllMbtiResult(PageConstant.page, PageConstant.limit, userMapper.selectByPrimaryKey(userId));
        MbtiResultVo mbtiResultVo = mbtiResultVos.get(0);

        mbtiResultVo = setMbtiResultVoResult(mbtiResultVo);
        model.addAttribute("mbtiResultVo", mbtiResultVo);
        // 返回到检测结果页面
        return "analysis/mbti/mbtiResult";
    }


    // 重新检测
    @RequestMapping("/repeatMbtiTest")
    public String repeatMbtiTest()
    {
        return "analysis/mbtiTestQuestion";
    }

    // 设置检测结果
    private MbtiResultVo setMbtiResultVoResult(MbtiResultVo mbtiResultVo){
        String result = "";
        int E = mbtiResultVo.getE();
        int I = mbtiResultVo.getI();

        int S = mbtiResultVo.getS();
        int N = mbtiResultVo.getN();

        int T = mbtiResultVo.getT();
        int F = mbtiResultVo.getF();

        int J = mbtiResultVo.getJ();
        int P = mbtiResultVo.getP();

        if (mbtiResultVo.getE() > mbtiResultVo.getI()) result += "E";
        else result+="I";

        if (mbtiResultVo.getS() > mbtiResultVo.getN()) result += "S";
        else result+="N";

        if (mbtiResultVo.getT() > mbtiResultVo.getF()) result += "T";
        else result+="F";

        if (mbtiResultVo.getJ() > mbtiResultVo.getP()) result += "J";
        else result+="P";

        mbtiResultVo.setResult(result);


        int peN = (int)(E / (E*1.0 + I) * 100);

        int psN = (int)(S / (S*1.0 + N) * 100);

        int ptN = (int)(T / (T*1.0 + F) * 100);

        int pjN = (int)(J / (J*1.0 + P) * 100);

        mbtiResultVo.setPe(peN+"%");
        mbtiResultVo.setPi((100-peN)+"%");

        mbtiResultVo.setPs(psN+"%");
        mbtiResultVo.setPn((100-psN)+"%");

        mbtiResultVo.setPt(ptN+"%");
        mbtiResultVo.setPf((100-ptN)+"%");

        mbtiResultVo.setPj(pjN+"%");
        mbtiResultVo.setPp((100-pjN)+"%");


        return mbtiResultVo;
    }
}
