package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.dao.Article;
import com.zxnk.mapper.ArticleMapper;
import com.zxnk.service.ArticleService;
import com.zxnk.util.ResponseResult;
import com.zxnk.util.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ArticleServiceImpl
 * @Description TODO
 * @Author cc
 * @Date 2023/4/25 10:36
 * @Version 1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> findAll() {
        return articleMapper.selectList(null);
    }

    /**
     * @return: com.zxnk.util.ResponseResult<java.util.List<com.zxnk.util.ResponseResult>>
     * @decription 需要查询浏览量最高的前10篇文章的信息,不能把草稿展示出来，不能把删除了的文章查询出来。要按照浏览量进行降序排序。
     * @date 2023/4/25 11:25
    */
    @Override
    public List<Article> hotArticleList() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //排除草稿
        wrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_NORMAL);
        //降序
        wrapper.orderByDesc(Article::getViewCount);
        //最多10条
        wrapper.last("limit 10");
        List<Article> articles = articleMapper.selectList(wrapper);
        //Page<Article> page = articleMapper.selectPage(new Page<Article>(1, 10), wrapper);
        //List<Article> records = page.getRecords();
        return articles;
    }
}