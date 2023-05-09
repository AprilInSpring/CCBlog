package com.zxnk.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxnk.entity.Article;
import com.zxnk.dto.ArticleDetailVo;
import com.zxnk.dto.ArticleVo;
import com.zxnk.dto.HotArticleVo;
import com.zxnk.dto.PageVo;
import com.zxnk.service.ArticleService;
import com.zxnk.service.CategoryService;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "博客文章控制类",description = "博客文章相应接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @ApiOperation(value = "获取全部文章")
    public List<Article> findAll(){
        return articleService.findAll();
    }

    /**
     * @return: com.zxnk.util.ResponseResult<java.util.List<com.zxnk.entity.HotArticleVo>>
     * @decription 返回最做多点击的10条博文，排除草稿
     * @date 2023/4/25 13:30
    */
    @GetMapping("/hotArticleList")
    @ApiOperation("返回最做多点击的10条博文，排除草稿")
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
    @ApiOperation("根据分页条件，对博文进行分页查询，并判断是否选择同一类别下的博文，并且博文的状态必须是正常的，而且对数据采用置顶的排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "分页页号"),
            @ApiImplicitParam(name = "pageSize",value = "分页大小"),
            @ApiImplicitParam(name = "categoryId",value = "文章类别id")
    })
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

    @ApiOperation("根据id查询相应的文章详情")
    @GetMapping("/{id}")
    @ApiImplicitParam(name = "id",value = "文章id")
    public ResponseResult<ArticleDetailVo> getArticleDetailById(@PathVariable Long id){
        return ResponseResult.okResult(articleService.findById(id));
    }

    @ApiOperation("根据id更新文章的浏览量")
    @PutMapping("/updateViewCount/{id}")
    @ApiImplicitParam(name = "id",value = "文章id")
    public ResponseResult updateViewCount(@PathVariable Long id){
        return articleService.updateViewCountById(id);
    }
}