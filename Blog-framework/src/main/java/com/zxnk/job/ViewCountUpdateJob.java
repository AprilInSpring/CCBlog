package com.zxnk.job;

import com.zxnk.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ViewCountUpdateJob
 * @Description 博文浏览量定时更新任务
 * @Author cc
 * @Date 2023/5/7 17:14
 * @Version 1.0
 */
@Component
public class ViewCountUpdateJob {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ArticleService articleService;

    //定时任务，每隔两分半中同步一次数据库，完成博文浏览量的更新
    @Scheduled(cron = "30 2/2 * * * *")
    public void updateViewCount(){
        Map<String,Integer> viewCount = redisTemplate.opsForHash().entries("viewCount");
        //完成数据库的更新
        articleService.updateArticles(viewCount);
    }
}