package com.zxnk.starter;

import com.zxnk.entity.Article;
import com.zxnk.entity.Audience;
import com.zxnk.service.ArticleService;
import com.zxnk.service.AudienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ViewCountRunner
 * @Description 数据预热类，在应用启动成功时，查询数据库，将所有的文章浏览次数存入redis
 * @Author cc
 * @Date 2023/5/7 17:01
 * @Version 1.0
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private AudienceService audienceService;
    @Autowired
    private RedisTemplate redisTemplate;

    //启动成功时执行的方法
    @Override
    public void run(String... args) throws Exception {
        //查询所有博文
        List<Article> articles = articleService.findAll();
        //将博文集合转成hashmap存入redis中
        Map<String, Integer> map = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        //存入redis中
        redisTemplate.opsForHash().putAll("viewCount",map);

        //查询所有的浏览数,放入redis中，进行记录
        int size = audienceService.findAll().size();
        redisTemplate.opsForValue().set("count",size);
    }
}