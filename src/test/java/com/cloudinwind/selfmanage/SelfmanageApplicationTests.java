package com.cloudinwind.selfmanage;

import com.cloudinwind.selfmanage.entity.User;
import com.cloudinwind.selfmanage.entity.forum.Ad;
import com.cloudinwind.selfmanage.enums.NewsColumnEnum;
import com.cloudinwind.selfmanage.mapper.AdMapper;


import com.cloudinwind.selfmanage.provider.AliProvider;
import com.cloudinwind.selfmanage.service.analysis.UserMbtiresultService;
import com.cloudinwind.selfmanage.vo.analysis.MbtiResultVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SelfmanageApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    AdMapper adMapper;

    @Resource
    UserMbtiresultService userMbtiresultService;

    @Test
    public void testQuestion()
    {
        Ad ad = new Ad();
        ad.setTitle("!11");
        ad.setGmtCreate(111l);
        ad.setGmtEnd(222l);
        ad.setImage("!11");
        ad.setUrl("@22");
        ad.setStatus(111);
        ad.setGmtModified(111l);
        ad.setGmtCreate(111l);
        ad.setPos("111");
        adMapper.insert(ad);
    }

    @Test
    public void teatGetMbtiResult()
    {
        User user = new User();
        user.setId(777l);
        List<MbtiResultVo> mbtiResultVos = userMbtiresultService.selectAllMbtiResult(1, 10, user);
        for (MbtiResultVo resultVo: mbtiResultVos){
            System.out.println("result:"+resultVo.toString());
        }
    }

    @Resource
    AliProvider provider;

    @Test
    public void updateNews(){
        provider.autoGetNews(NewsColumnEnum.NEWS_COLUMN_GUONEI.getStrId(),5);
    }
}
