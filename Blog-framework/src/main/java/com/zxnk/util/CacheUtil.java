package com.zxnk.util;

import com.zxnk.service.ArticleService;
import com.zxnk.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName CacheUtil
 * @Description 文章缓存工具类，进行文章详情缓存的删除，热点文章详情的更新，文章列表详情的更新
 * @Author cc
 * @Date 2023/7/5 15:43
 * @Version 1.0
 */
@Component
@Slf4j
public class CacheUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    //进行缓存刷新，用于更新文章浏览数
    public void updateCache(){
        //删除缓存
        redisTemplate.delete("hotArticleList");
        redisTemplate.delete("articleList");
        log.info("完成浏览量更新和缓存删除");
        //重新加载缓存
        articleService.findAll();
        articleService.hotArticleList();
    }

    //更新类别缓存
    public void updateCategoryCache(){
        redisTemplate.delete("category");
        categoryService.getCategoryList();
    }

    //删除指定缓存
    public void deleteCache(String key){
        redisTemplate.delete(key);
    }
}