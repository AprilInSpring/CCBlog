package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.dto.CategoryVo;
import com.zxnk.dto.PageVo;
import com.zxnk.entity.Article;
import com.zxnk.entity.Category;
import com.zxnk.mapper.CategoryMapper;
import com.zxnk.service.ArticleService;
import com.zxnk.service.CategoryService;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.CacheUtil;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
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
    private ArticleService articleService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CacheUtil cacheUtil;

    /**
     * @return: java.util.List<com.zxnk.entity.Category>
     * @decription 查询可用的博文分类列表，并且该分类下必须有博文
     * @date 2023/4/26 9:01
    */
    @Override
    public List<Category> getCategoryList() {
        //先走缓存
        List<Category> categoryList = (List<Category>) redisTemplate.opsForValue().get("category");
        if (!Objects.isNull(categoryList)) {
            return categoryList;
        }
        //先查询所有正常状态博文
        //List<Article> articlesList = articleService.findAll();
        //并根据这些博文获取相应的博文分类(使用stream流实现快速转换)
        /*List<Long> categoryIdList = articlesList.stream()
                                        .map(article -> article.getCategoryId())
                                        .distinct()
                                        .collect(Collectors.toList());*/

        //采用自定义sql，并创建索引，加快访问速度
        List<Long> categoryIdList = articleService.getAllCategoryId();
        //根据id进行数据的进一步封装，实现数据的完成化(博文分类名称)
        categoryList = categoryIdList.stream()
                .map(id -> this.getCategoryById(id))
                .filter(category -> category.getDelFlag() == 0) //排除已删除的类别
                .collect(Collectors.toList());
        //永不过期，当修改、添加文章或者添加、修改别的时候才进行缓存的刷新
        redisTemplate.opsForValue().set("category",categoryIdList);
        return categoryList;
    }

    /**
     * @param id 博文分类id
     * @return: com.zxnk.entity.Category
     * @decription 根据博文id获取博文对象
     * @date 2023/4/26 9:15
    */
    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.selectById(id);
    }

    /**
     * @return: java.util.List<com.zxnk.entity.Category>
     * @decription 查询所有可用的博文分类
     * @date 2023/5/9 16:48
     */
    @Override
    public ResponseResult getAll() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus,"0");
        List<Category> categories = categoryMapper.selectList(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeans(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    public List<Category> getAllCategories() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus,"0");
        List<Category> categories = categoryMapper.selectList(wrapper);
        return categories;
    }

    //分页查询分类信息
    @Override
    public ResponseResult selectAll(Integer pageNum, Integer pageSize, String name, String status) {
        //构建查询数据
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        //精确查询状态
        wrapper.eq(StringUtils.hasText(status),Category::getStatus,status);
        //模糊查询名称
        wrapper.like(StringUtils.hasText(name),Category::getName,name);
        //分页查询
        Page<Category> categories = categoryMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return ResponseResult.okResult(new PageVo(categories.getRecords(),categories.getTotal()));
    }

    //新增分类对象
    @Override
    public ResponseResult addCategory(Category category) {
        categoryMapper.insert(category);
        return ResponseResult.okResult();
    }

    //查询分类对象
    @Override
    public ResponseResult getById(long id) {
        return ResponseResult.okResult(categoryMapper.selectById(id));
    }

    //更新分类对象
    @Override
    public ResponseResult updateById(Category category) {
        categoryMapper.updateById(category);
        return ResponseResult.okResult();
    }

    //根据id删除分类对象
    @Override
    public ResponseResult deleteById(long id) {
        categoryMapper.deleteById(id);
        //更新类别缓存
        cacheUtil.updateCategoryCache();
        return ResponseResult.okResult();
    }
}