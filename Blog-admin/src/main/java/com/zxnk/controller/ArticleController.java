package com.zxnk.controller;

import com.zxnk.dao.Article;
import com.zxnk.entity.HotArticleVo;
import com.zxnk.service.ArticleService;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}