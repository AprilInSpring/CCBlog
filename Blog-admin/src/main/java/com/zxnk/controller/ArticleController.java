package com.zxnk.controller;

import com.zxnk.dto.AddArticleDto;
import com.zxnk.dto.AdminArticle;
import com.zxnk.service.ArticleService;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ArticleController
 * @Description 后台博文管理器
 * @Author cc
 * @Date 2023/5/9 17:07
 * @Version 1.0
 */
@RestController
@RequestMapping("/content")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * @param articleDto 博文数据对象，含有标签数据
     * @return: com.zxnk.util.ResponseResult
     * @decription 添加博文数据对象，并维护标签数据
     * @date 2023/5/9 17:19
    */
    @PostMapping("/article")
    public ResponseResult addArticle(@RequestBody AddArticleDto articleDto){
        return articleService.addArticle(articleDto);
    }

    /**
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param title 标题
     * @param summary 摘要
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据分页数据和文章信息，进行文章的相应查询
     * @date 2023/5/10 8:58
    */
    @GetMapping("/article/list")
    public ResponseResult selectAll(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    String title,String summary){
        return articleService.selectAll(pageNum,pageSize,title,summary);
    }

    /**
     * @param id 博文id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据博文id返回文章数据，要求包含标签数据
     * @date 2023/5/10 9:16
    */
    @GetMapping("/article/{id}")
    public ResponseResult getArticleById(@PathVariable Long id){
        return articleService.selectById(id);
    }

    /**
     * @param adminArticle 后台文章修改对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 完成后台文章的修改
     * @date 2023/5/10 9:31
    */
    @PutMapping("/article")
    public ResponseResult updateArticle(@RequestBody AdminArticle adminArticle){
        return articleService.updateArticle(adminArticle);
    }

    /**
     * @param id 博文id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据博文id进行数据的逻辑删除
     * @date 2023/5/10 9:44
    */
    @DeleteMapping("/article/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id){
        return articleService.deleteById(id);
    }
}