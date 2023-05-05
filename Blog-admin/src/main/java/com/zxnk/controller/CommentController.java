package com.zxnk.controller;

import com.zxnk.entity.CommentVo;
import com.zxnk.service.CommentService;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CommentController
 * @Description TODO
 * @Author cc
 * @Date 2023/5/4 21:18
 * @Version 1.0
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * @param articleId 博文id
     * @param pageNum   分页的页码
     * @param pageSize  分页的大小
     * @return: com.zxnk.util.ResponseResult<java.util.List<com.zxnk.entity.CommentVo>>
     * @decription  根据博文id查询相应的评论包括相应的子评论（只含二级）
     * @date 2023/5/4 21:20
    */
    @GetMapping("/commentList")
    public ResponseResult<List<CommentVo>> commentList(@RequestParam(value = "articleId",required = true) Long articleId,@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "4") Integer pageSize){
        return commentService.commentList(articleId,pageNum,pageSize);
    }
}