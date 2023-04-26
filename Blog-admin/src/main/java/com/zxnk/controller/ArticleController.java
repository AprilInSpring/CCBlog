package com.zxnk.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.dao.Article;
import com.zxnk.entity.ArticleDetailVo;
import com.zxnk.entity.ArticleVo;
import com.zxnk.entity.HotArticleVo;
import com.zxnk.entity.PageVo;
import com.zxnk.service.ArticleService;
import com.zxnk.service.CategoryService;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ArticleController
 * @Description TODO
 * @Author cc
 * @Date 2023/4/25 10:46
 * @Version 1.0
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public List<Article> findAll(){
        return articleService.findAll();
    }

    /**
     * @return: com.zxnk.util.ResponseResult<java.util.List<com.zxnk.entity.HotArticleVo>>
     * @decription 返回最做多点击的10条博文，排除草稿
     * @date 2023/4/25 13:30
    */
    @GetMapping("/hotArticleList")
    public ResponseResult<List<HotArticleVo>> hotArticleList(){
        List<Article> result = articleService.hotArticleList();
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeans(result, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVos);
    }

    /**
     * @param pageNum 初始页
     * @param pageSize 每页条数
     * @param categoryId 博文类别id
     * @return: com.zxnk.util.ResponseResult<com.zxnk.entity.PageVo>
     * @decription 根据分页条件，对博文进行分页查询，并判断是否选择同一类别下的博文，并且博文的状态必须是正常的，而且对数据采用置顶的排序
     * @date 2023/4/26 10:39
    */
    @GetMapping("/articleList")
    public ResponseResult<PageVo> articleList(@RequestParam(defaultValue = "0")Integer pageNum,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              Long categoryId){
        //获取分页对象
        Page<Article> articlePage = articleService.articleList(pageNum, pageSize, categoryId);
        //获取博文对象
        List<Article> articleList = articlePage.getRecords();
        //完成数据的封装
        List<ArticleVo> articleVoList = BeanCopyUtils.copyBeans(articleList, ArticleVo.class);
        PageVo pageVo = new PageVo(articleVoList, articlePage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @GetMapping("/{id}")
    public ResponseResult<ArticleDetailVo> getArticleDetailById(@PathVariable Long id){
        return ResponseResult.okResult(articleService.findById(id));
    }
}