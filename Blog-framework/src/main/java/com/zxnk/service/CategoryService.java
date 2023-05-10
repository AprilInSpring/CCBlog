package com.zxnk.service;

import com.zxnk.entity.Category;
import com.zxnk.util.ResponseResult;

import java.util.List;

public interface CategoryService {
    //查询可用的文章列表,且该分类下必须有文章
    List<Category> getCategoryList();

    Category getCategoryById(Long id);

    //查询所有可用状态的博文分类
    ResponseResult getAll();

    //查询所有可用的博文分类
    public List<Category> getAllCategories();

    ResponseResult selectAll(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(Category category);

    ResponseResult getById(long id);

    ResponseResult updateById(Category category);

    ResponseResult deleteById(long id);
}
