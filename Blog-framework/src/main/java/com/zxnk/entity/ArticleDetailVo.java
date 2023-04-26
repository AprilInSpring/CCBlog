package com.zxnk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName ArticleDetailVo
 * @Description 与前端交互的文章详情类
 * @Author cc
 * @Date 2023/4/26 21:49
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {

    //id
    private Long id;

    //文章标题
    private String title;

    //提要
    private String summary;

    //文章内容
    private String content;

    //分类id
    private Long categoryId;

    //分类名称
    private String categoryName;

    //缩略图
    private String thumbnail;

    //浏览量
    private Long viewCount;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createTime;

}