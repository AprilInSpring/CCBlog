package com.zxnk.service;

import com.zxnk.dao.Comment;
import com.zxnk.entity.CommentVo;
import com.zxnk.util.ResponseResult;

import java.util.List;

//评论接口
public interface CommentService {
    ResponseResult<List<CommentVo>> commentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);

    ResponseResult linkCommentList(Integer pageNum, Integer pageSize);
}
