package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.dto.AddArticleDto;
import com.zxnk.entity.Article;
import com.zxnk.entity.ArticleTag;
import com.zxnk.entity.Category;
import com.zxnk.dto.ArticleDetailVo;
import com.zxnk.mapper.ArticleMapper;
import com.zxnk.mapper.ArticleTagMapper;
import com.zxnk.service.ArticleService;
import com.zxnk.service.CategoryService;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.ResponseResult;
import com.zxnk.util.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    /**
     * @return: java.util.List<com.zxnk.entity.Article>
     * @decription 查询所有可状态正常的博文
     * @date 2023/4/26 9:05
    */
    @Override
    public List<Article> findAll() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //状态正常
        wrapper.eq(Article::getStatus,SystemConstant.ARTICLE_STATUS_NORMAL);
        //未逻辑删除
        wrapper.eq(Article::getDelFlag,SystemConstant.ARTICLE_STATUS_NORMAL);
        return articleMapper.selectList(wrapper);
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

    /**
     * @param pageNum 初始页
     * @param pageSize 每页大小
     * @param categoryId 博客分类id
     * @return: java.util.List<com.zxnk.entity.Article>
     * @decription 根据参数完成博客文章的分类查询，当存在博客类别id的时候，根据博文分类id进行分页，否则进行全分页
     * @date 2023/4/26 10:13
    */
    @Override
    public Page<Article> articleList(Integer pageNum,Integer pageSize,Long categoryId) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //状态正常
        wrapper.eq(Article::getStatus,SystemConstant.ARTICLE_STATUS_NORMAL);
        wrapper.eq(Article::getDelFlag,SystemConstant.ARTICLE_STATUS_NORMAL);
        //是否根据博文分类id分页,eq方法的重载，若第一个条件成立，则加入该条件限制查询
        wrapper.eq(Objects.nonNull(categoryId)&&categoryId > 0,Article::getCategoryId,categoryId);
        //排序，根据isTop字段，进行降序排序
        wrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> articlePage = articleMapper.selectPage(new Page<Article>(pageNum, pageSize), wrapper);
        articlePage.getRecords().stream()
                .forEach(article -> {
                    Category category = categoryService.getCategoryById(article.getCategoryId());
                    article.setCategoryName(category.getName());
                });
        return articlePage;
    }

    /**
     * @param id 博文id
     * @return: com.zxnk.entity.ArticleDetailVo
     * @decription 根据博文id查询博文对象，并完成数据的封装，返回给前端显示（需要查询出分类名称）
     * @date 2023/4/26 21:59
    */
    @Override
    public ArticleDetailVo findById(Long id) {
        //查询相应的文章对象
        Article article = articleMapper.selectById(id);
        //完成浏览量的即时更新
        Integer viewCount = (Integer) redisTemplate.opsForHash().get("viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //完成数据的封装
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        articleDetailVo.setCategoryName(categoryService.getCategoryById(articleDetailVo.getCategoryId()).getName());
        return articleDetailVo;
    }

    /**
     * @param id 博文id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据id实现博文浏览量的自增
     * @date 2023/5/7 17:11
    */
    @Override
    public ResponseResult updateViewCountById(Long id) {
        //完成redis中hashValue值的自增1，第一个参数为缓存的键，第二个为hash键，第三个为自增的幅度
        redisTemplate.opsForHash().increment("viewCount",id.toString(),1l);
        return ResponseResult.okResult();
    }

    /**
     * @param viewCount 博文浏览量集合
     * @return: void
     * @decription 根据博文id，批量更新博文浏览量
     * @date 2023/5/7 17:22
    */
    @Override
    public void updateArticles(Map<String, Integer> viewCount) {
        viewCount.entrySet().forEach(new Consumer<Map.Entry<String, Integer>>() {
            @Override
            public void accept(Map.Entry<String, Integer> stringIntegerEntry) {
                //获取id
                Long id = Long.valueOf(stringIntegerEntry.getKey());
                //获取viewCount
                Long viewCount = Long.valueOf(stringIntegerEntry.getValue());
                //进行更新
                articleMapper.updateById(Article.builder().id(id).viewCount(viewCount).build());
            }
        });
    }

    /**
     * @param articleDto 博文数据类
     * @return: com.zxnk.util.ResponseResult
     * @decription 添加博文数据对象，并维护博文标签表
     * @date 2023/5/9 17:18
    */
    @Override
    @Transactional
    public ResponseResult addArticle(AddArticleDto articleDto) {
        //转换成article对象
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        articleMapper.insert(article);

        //维护博文标签表
        List<Long> tags = articleDto.getTags();
        //获取文章id
        Long articleId = article.getId();
        tags.stream()
                //转成博客标签对象
                .map(tag -> new ArticleTag(articleId, tag))
                //完成博文标签表的新增
                .forEach(articleTag -> articleTagMapper.insert(articleTag));
        return ResponseResult.okResult();
    }
}