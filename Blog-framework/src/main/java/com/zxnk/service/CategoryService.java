package com.zxnk.service;

import com.zxnk.entity.Category;

import java.util.List;

public interface CategoryService {
    //查询可用的文章列表
    List<Category> getCategoryList();

    Category getCategoryById(Long id);
}
