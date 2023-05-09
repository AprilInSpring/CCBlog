package com.zxnk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName PageVo
 * @Description 表单分页对象
 * @Author cc
 * @Date 2023/4/26 10:04
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {
    //数据对象
    private List rows;
    //数据条目
    private Long total;
}