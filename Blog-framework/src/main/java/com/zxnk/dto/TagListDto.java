package com.zxnk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TagListDto
 * @Description 前端搜索参数封装类
 * @Author cc
 * @Date 2023/5/9 13:36
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagListDto {

    //标签名
    private String name;
    //标记
    private String remark;
}