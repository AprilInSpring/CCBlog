package com.zxnk.controller;

import com.zxnk.dto.AddArticleDto;
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

}