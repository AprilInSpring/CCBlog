package com.zxnk.job;

import com.zxnk.entity.Audience;
import com.zxnk.service.ArticleService;
import com.zxnk.service.AudienceService;
import com.zxnk.util.CacheUtil;
import com.zxnk.util.DateUtil;
import com.zxnk.util.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
public class ScheduleJob {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private AudienceService audienceService;
    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private CacheUtil cacheUtil;

    //定时任务，每天同步一次数据库，完成博文浏览量的更新，删除缓存
    @Scheduled(cron = "0 0 0 * * *")
    public void updateViewCount(){
        Map<String,Integer> viewCount = redisTemplate.opsForHash().entries("viewCount");
        //完成数据库的更新
        articleService.updateArticles(viewCount);
        //更新缓存(需要考虑多线程并发问题，暂缓)
        cacheUtil.updateCache();
    }

    //定时任务，每天晚上22点，删除今天的浏览数据
    @Scheduled(cron = "0 0 22 * * *")
    public void updateAudience(){
        List<Audience> audience = redisTemplate.opsForList().range("audience", 0, -1);
        //清楚缓存
        redisTemplate.delete("audience");
        //完成数据库更新
        audienceService.updateAudience(audience);
        //获取总浏览数
        Integer count = (Integer) redisTemplate.opsForValue().get("count");
        log.info("完成浏览记录的更新");
        //发送通知邮件
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("今日的访问游客:");
        stringBuilder.append("<BR/>");
        audience.forEach(temp -> {
            stringBuilder.append("ip:"+temp.getIp()+"  ");
            stringBuilder.append("归属地:"+temp.getAddress()+"  ");
            stringBuilder.append("访问时间:"+ DateUtil.format(temp.getDate()));
            //换行
            stringBuilder.append("<BR/>");
        });
        stringBuilder.append("今天共"+audience.size()+"人浏览你的博客,目前的总浏览数为"+count);
        //mailUtils.sendMail(stringBuilder.toString(),"今日浏览数据");
        mailUtils.sendToManagers(stringBuilder.toString(),"今日浏览数据");

    }

}