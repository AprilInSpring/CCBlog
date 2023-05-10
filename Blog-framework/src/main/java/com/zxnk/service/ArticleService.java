package com.zxnk.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.dto.AddArticleDto;
import com.zxnk.dto.AdminArticle;
import com.zxnk.entity.Article;
import com.zxnk.dto.ArticleDetailVo;
import com.zxnk.util.ResponseResult;

import java.util.List;
import java.util.Map;

public interface ArticleService{
    List<Article> findAll();

    List<Article> hotArticleList();

    Page<Article> articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ArticleDetailVo findById(Long id);

    ResponseResult updateViewCountById(Long id);

    void updateArticles(Map<String, Integer> viewCount);

    ResponseResult addArticle(AddArticleDto articleDto);

    ResponseResult selectAll(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult selectById(Long id);

    ResponseResult updateArticle(AdminArticle adminArticle);

    ResponseResult deleteById(Long id);
}
