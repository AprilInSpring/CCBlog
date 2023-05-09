package com.zxnk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName HotArticleVo
 * @Description 热点文章处理类，对数据进行二次处理
 * @Author cc
 * @Date 2023/4/25 13:10
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVo {
    //博文id
    private Long id;

    //博文题目
    private String title;

    //博文浏览数
    private Long viewCount;
}