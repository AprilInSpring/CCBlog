package com.zxnk.controller;

import com.zxnk.entity.Comment;
import com.zxnk.dto.CommentVo;
import com.zxnk.service.CommentService;
import com.zxnk.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "评论控制类",description = "评论相应接口")
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
    @ApiOperation("根据博文id查询相应的评论包括相应的子评论（只含二级）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "分页页号"),
            @ApiImplicitParam(name = "pageSize",value = "分页大小"),
            @ApiImplicitParam(name = "articleId",value = "文章id")
    })
    public ResponseResult<List<CommentVo>> commentList(@RequestParam(value = "articleId",required = true) Long articleId,
                                                       @RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "4") Integer pageSize){
        return commentService.commentList(articleId,pageNum,pageSize);
    }

    @ApiOperation("在文章或者友链发表评论")
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @ApiOperation("查询友链相应的评论列表，包括并且只包含二级子评论")
    @GetMapping("/linkCommentList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "分页页号"),
            @ApiImplicitParam(name = "pageSize",value = "分页大小"),
    })
    public ResponseResult linkCommentList(@RequestParam(defaultValue = "0") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize){
        return commentService.linkCommentList(pageNum,pageSize);
    }
}