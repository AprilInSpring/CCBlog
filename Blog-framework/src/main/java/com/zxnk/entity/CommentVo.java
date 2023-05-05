package com.zxnk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private Long id;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //所回复的目标评论的昵称
    private String toCommentUserName;
    //回复目标评论id
    private Long toCommentId;

    private Long createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;

    //发送评论的用户昵称
    private String username;

    //子评论集合
    private List<CommentVo> children;
}