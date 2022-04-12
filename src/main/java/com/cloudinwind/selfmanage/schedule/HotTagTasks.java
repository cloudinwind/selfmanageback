package com.cloudinwind.selfmanage.schedule;

import com.cloudinwind.selfmanage.cache.HotTagCache;
import com.cloudinwind.selfmanage.entity.forum.Question;
import com.cloudinwind.selfmanage.entity.forum.QuestionExample;
import com.cloudinwind.selfmanage.mapper.QuestionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

// 定时更新 标签
@Component
@Slf4j
public class HotTagTasks {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private HotTagCache hotTagCache;


    //权重计算priority=questionCount*5+commentCount*1+likeCount*10+viewCount/10
    @Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    public void hotTagSchedule() {
        int offset = 0;
        int limit = 20;
        log.info("hotTagSchedule start {}", new Date());
        List<Question> list = new ArrayList<>();

        Map<String, Integer> priorities = new HashMap<>();
        while (offset == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), ",");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null) {
                        priorities.put(tag, priority + 5 + question.getCommentCount()+(question.getLikeCount()*10)+(question.getViewCount()/10));
                    } else {
                        priorities.put(tag, 5 + question.getCommentCount()+(question.getLikeCount()*10)+(question.getViewCount()/10));
                    }
                }
            }
            offset += limit;
        }
        hotTagCache.updateTags(priorities);
        log.info("hotTagSchedule stop {}", new Date());
    }
}
