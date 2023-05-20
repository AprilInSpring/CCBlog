package com.zxnk.job;

import com.zxnk.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ViewCountUpdateJob
 * @Description 博文浏览量定时更新任务
 * 2023/5/20 完成一次功能更新，加入删除热点文章缓存数据和文章列表数据
 * @Author cc
 * @Date 2023/5/7 17:14
 * @Version 1.0
 */
@Component
@Slf4j
public class ViewCountUpdateJob {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ArticleService articleService;

    //定时任务，每隔十分中同步一次数据库，完成博文浏览量的更新，并删除hotArticleList的缓存
    @Scheduled(cron = "0 5/5 * * * *")
    public void updateViewCount(){
        Map<String,Integer> viewCount = redisTemplate.opsForHash().entries("viewCount");
        //完成数据库的更新
        articleService.updateArticles(viewCount);
        //删除缓存
        redisTemplate.delete("hotArticleList");
        redisTemplate.delete("articleList");
        log.info("完成浏览量更新和缓存删除");
    }
}