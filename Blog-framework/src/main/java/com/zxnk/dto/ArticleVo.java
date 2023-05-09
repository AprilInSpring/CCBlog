package com.zxnk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName ArticleVo
 * @Description 表单博文类，用于与前端完成对接
 * @Author cc
 * @Date 2023/4/26 10:02
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {

    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类名
    private String categoryName;
    //缩略图
    private String thumbnail;


    //访问量
    private Long viewCount;

    private Date createTime;


}