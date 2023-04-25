package com.zxnk.service;

import com.zxnk.dao.Article;
import com.zxnk.util.ResponseResult;

import java.util.List;

public interface ArticleService{
    List<Article> findAll();

    List<Article> hotArticleList();
}
