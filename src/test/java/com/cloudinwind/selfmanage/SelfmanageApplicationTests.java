package com.cloudinwind.selfmanage;

import com.cloudinwind.selfmanage.entity.forum.Ad;
import com.cloudinwind.selfmanage.mapper.AdMapper;

//import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class SelfmanageApplicationTests {

//    @Test
    void contextLoads() {
    }

    @Resource
    AdMapper adMapper;

//    @Test
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

}
