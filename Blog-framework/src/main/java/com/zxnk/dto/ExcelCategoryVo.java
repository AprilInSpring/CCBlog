package com.zxnk.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ExcelCategoryVo
 * @Description TODO
 * @Author cc
 * @Date 2023/5/9 21:01
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExcelCategoryVo {

    //name
    @ExcelProperty(value = "分类名")
    private String name;

    //描述
    @ExcelProperty(value = "描述")
    private String description;

    //状态
    @ExcelProperty(value = "状态")
    private String status;
}