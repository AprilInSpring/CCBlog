package com.zxnk.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.dao.Article;
import com.zxnk.entity.ArticleDetailVo;

import java.util.List;

public interface ArticleService{
    List<Article> findAll();

    List<Article> hotArticleList();

    Page<Article> articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ArticleDetailVo findById(Long id);
}
