package com.cloudinwind.selfmanage.schedule;


import com.cloudinwind.selfmanage.cache.TinifyPngCache;
import com.cloudinwind.selfmanage.dto.TinifyPngDTO;
import com.cloudinwind.selfmanage.provider.QCloudProvider;
import com.cloudinwind.selfmanage.provider.TinifyProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

// 定时压缩图片
@Component
@Slf4j
public class TinifyPngTasks {

    @Autowired
    private TinifyPngCache tinifyPngCache;
    @Autowired
    private TinifyProvider tinifyProvider;
    @Autowired
    private QCloudProvider qCloudProvider;

    @Scheduled(cron = "0 11 13,23 * * ?")
    public void tinifyPngSchedule() {
        log.info("tinifyPngSchedule start {}", new Date());
        List<TinifyPngDTO> images = tinifyPngCache.get();
        if(images.size()>0){
            for (TinifyPngDTO image : images) {
                try {
                    InputStream streamfromUrl = tinifyProvider.getStreamfromUrl(image.getUrl());
                    String img = qCloudProvider.uploadtoBucket(streamfromUrl, "img", "image/png", image.getUser(), image.getFileName(), (long)streamfromUrl.available());
                    log.info(img+"已压缩并上传覆盖 {}", new Date());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            tinifyPngCache.clear();
        }else {
            log.info("此时间段没有需要压缩的png图片 {}", new Date());
        }

        log.info("tinifyPngSchedule stop {}", new Date());
    }


}
