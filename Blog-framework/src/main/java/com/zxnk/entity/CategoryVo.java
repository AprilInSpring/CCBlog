package com.zxnk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CategoryVo
 * @Description 表单博文分类数据
 * @Author cc
 * @Date 2023/4/26 9:19
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryVo {
    //id
    private Long id;
    //name
    private String name;
}