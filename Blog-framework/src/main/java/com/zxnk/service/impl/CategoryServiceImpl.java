package com.zxnk.service.impl;

import com.zxnk.dao.Article;
import com.zxnk.dao.Category;
import com.zxnk.entity.CategoryVo;
import com.zxnk.mapper.ArticleMapper;
import com.zxnk.mapper.CategoryMapper;
import com.zxnk.service.ArticleService;
import com.zxnk.service.CategoryService;
import com.zxnk.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName CategoryServiceImpl
 * @Description 博文分类服务实现类
 * @Author cc
 * @Date 2023/4/26 8:59
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleService articleService; ;

    /**
     * @return: java.util.List<com.zxnk.dao.Category>
     * @decription 查询可用的博文分类列表，并且该分类下必须有博文
     * @date 2023/4/26 9:01
    */
    @Override
    public List<Category> getCategoryList() {
        //先查询所有正常状态博文
        List<Article> articlesList = articleService.findAll();
        //并根据这些博文获取相应的博文分类(使用stream流实现快速转换)
        List<Long> categoryIdList = articlesList.stream()
                                        .map(article -> article.getCategoryId())
                                        .distinct()
                                        .collect(Collectors.toList());
        //根据id进行数据的进一步封装，实现数据的完成化(博文分类名称)
        List<Category> categoryList = categoryIdList.stream()
                .map(id -> this.getCategoryById(id))
                .collect(Collectors.toList());
        return categoryList;
    }

    /**
     * @param id 博文分类id
     * @return: com.zxnk.dao.Category
     * @decription 根据博文id获取博文对象
     * @date 2023/4/26 9:15
    */
    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.selectById(id);
    }
}