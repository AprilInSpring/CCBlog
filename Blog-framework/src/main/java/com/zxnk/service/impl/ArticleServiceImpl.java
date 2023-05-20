package com.zxnk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.dto.AddArticleDto;
import com.zxnk.dto.AdminArticle;
import com.zxnk.dto.PageVo;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
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
@Slf4j
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
     * 2023/5/20 完成对此功能的更新，不再走数据库查询，而是直接从redis中拿数据，拿不到再走mysql，而且缓存永不失效，仅当后台curd之后
     *           再删除缓存
     * @date 2023/4/26 9:05
    */
    @Override
    public List<Article> findAll() {
        List<Article> articleList = (List<Article>) redisTemplate.opsForValue().get("articleList");
        //缓存没数据，走mysql，再更新缓存
        if(Objects.isNull(articleList)){
            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
            //状态正常
            wrapper.eq(Article::getStatus,SystemConstant.ARTICLE_STATUS_NORMAL);
            //未逻辑删除
            wrapper.eq(Article::getDelFlag,SystemConstant.ARTICLE_STATUS_NORMAL);
            articleList = articleMapper.selectList(wrapper);
            redisTemplate.opsForValue().set("articleList",articleList);
            log.info("-----------查询所有文章没走缓存----------");
        }
        return articleList;
    }

    /**
     * @return: com.zxnk.util.ResponseResult<java.util.List<com.zxnk.util.ResponseResult>>
     * @decription 需要查询浏览量最高的前10篇文章的信息,不能把草稿展示出来，不能把删除了的文章查询出来。要按照浏览量进行降序排序。
     * 2023/5/20 完成对此数据的功能更新，不再即时查mysql，而是从redis中获取数据，数据与定时任务同步更新，当定时任务执行后，会删除缓存
     *           约定redis中的key为 hotArticleList
     * @date 2023/4/25 11:25
    */
    @Override
    public List<Article> hotArticleList() {
        List<Article> articles = (List<Article>) redisTemplate.opsForValue().get("hotArticleList");
        //缓存为null，走mysql，再更新redis
        if(Objects.isNull(articles)){
            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
            //排除草稿
            wrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_NORMAL);
            //降序
            wrapper.orderByDesc(Article::getViewCount);
            //最多10条
            wrapper.last("limit 10");
            articles = articleMapper.selectList(wrapper);
            //Page<Article> page = articleMapper.selectPage(new Page<Article>(1, 10), wrapper);
            //List<Article> records = page.getRecords();
            redisTemplate.opsForValue().set("hotArticleList",articles);
            log.info("-----------查询热点文章没走缓存----------");
        }
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
     * 2023/5/20 对其完成功能更新，不再直接从mysql查询数据，而是先查询缓存，有则直接返回，没有则查询mysql再更新redis
     *           规定redis中文章详情数据的缓存key为  articleVo:${id},不再即时更新viewcount,而是交给定时任务统一更新
     * @date 2023/4/26 21:59
    */
    @Override
    public ArticleDetailVo findById(Long id) {
        //先查询redis
        ArticleDetailVo articleDetailVo = (ArticleDetailVo) redisTemplate.opsForValue().get("articleVo"+id);
        //缓存中不存在数据
        if(Objects.isNull(articleDetailVo)){
            //查询相应的文章对象
            Article article = articleMapper.selectById(id);
            //完成浏览量的即时更新
            //Integer viewCount = (Integer) redisTemplate.opsForHash().get("viewCount", id.toString());
            //article.setViewCount(viewCount.longValue());
            //完成数据的封装
            articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
            articleDetailVo.setCategoryName(categoryService.getCategoryById(articleDetailVo.getCategoryId()).getName());
            //存入缓存,时限为10min
            redisTemplate.opsForValue().set("articleVo"+id,articleDetailVo,10, TimeUnit.MINUTES);
        }
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
     * 2023/5/20 新增redis缓存删除
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
        redisTemplate.delete("articleList");
        return ResponseResult.okResult();
    }

    /**
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param title 标题
     * @param summary 摘要
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据分页数据和文章信息，进行文章的相应查询(包括草稿)
     * @date 2023/5/10 9:00
    */
    @Override
    public ResponseResult selectAll(Integer pageNum, Integer pageSize, String title, String summary) {
        //构建查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //如果文章标题有数据就根据标题进行模糊查询
        wrapper.like(StringUtils.hasText(title),Article::getTitle,title);
        //如果文章摘要有数据，就根据摘要进行模糊查询
        wrapper.like(StringUtils.hasText(summary),Article::getSummary,summary);
        //分页查询数据
        Page<Article> articlePage = articleMapper.selectPage(new Page<Article>(pageNum, pageSize), wrapper);
        //返回封装数据
        return ResponseResult.okResult(new PageVo(articlePage.getRecords(),articlePage.getTotal()));
    }

    /**
     * @param id 博文id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据博文id查询文章数据，并返回相应的标签列表，进行数据封装
     * @date 2023/5/10 9:18
    */
    @Override
    public ResponseResult selectById(Long id) {
        //查询对应的文章数据
        Article article = articleMapper.selectById(id);
        //完成数据转换
        AdminArticle adminArticle = BeanCopyUtils.copyBean(article, AdminArticle.class);
        //查询相应的标签id
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,article.getId());
        //完成数据转换
        List<Long> tags = articleTagMapper.selectList(wrapper).stream()
                .map(articleTag -> articleTag.getTagId())
                .collect(Collectors.toList());
        //封装标签数据
        adminArticle.setTags(tags);
        //返回数据
        return ResponseResult.okResult(adminArticle);
    }

    /**
     * @param adminArticle 修改文章对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 完成文章对象的修改,思想：先删除全部的文章标签列表，再加入
     * 2023/5/20 删除缓存更新
     * @date 2023/5/10 9:32
    */
    @Override
    public ResponseResult updateArticle(AdminArticle adminArticle) {
        //完成数据转换
        Article article = BeanCopyUtils.copyBean(adminArticle, Article.class);
        if(!StringUtils.hasText(article.getThumbnail())){
            //解决没有缩略图也存在图片的bug
            article.setThumbnail(null);
        }
        //完成博文修改
        articleMapper.updateById(article);
        //获取相应的博文标签
        List<ArticleTag> tags = adminArticle.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        //删除原来的文章标签数据
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagMapper.delete(wrapper);
        //完成文章标签数据的插入
        tags.forEach(tag -> articleTagMapper.insert(tag));
        redisTemplate.delete("articleList");
        return ResponseResult.okResult();
    }

    /**
     * @param id 博文id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据博文id进行数据的逻辑删除
     * 2023/5/20 删除缓存更新
     * @date 2023/5/10 9:44
    */
    @Override
    public ResponseResult deleteById(Long id) {
        articleMapper.deleteById(id);
        redisTemplate.delete("articleList");
        return ResponseResult.okResult();
    }


}