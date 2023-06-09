package com.zxnk.controller;

import com.zxnk.entity.Category;
import com.zxnk.dto.CategoryVo;
import com.zxnk.service.CategoryService;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CategoryController
 * @Description 博文分类控制层
 * @Author cc
 * @Date 2023/4/26 9:24
 * @Version 1.0
 */
@RestController
@RequestMapping("/category")
@Api(tags = "博客类别控制类",description = "博客类别相应接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @return: com.zxnk.util.ResponseResult<java.util.List<com.zxnk.entity.CategoryVo>>
     * @decription 返回状态正常的博文分类列表
     * @date 2023/4/26 9:27
    */
    @GetMapping("/getCategoryList")
    @ApiOperation("返回状态正常的博文分类列表")
    public ResponseResult<List<CategoryVo>> getCategoryList(){
        List<Category> categoryList = categoryService.getCategoryList();
        List<CategoryVo> categoryVoList = BeanCopyUtils.copyBeans(categoryList, CategoryVo.class);
        return ResponseResult.okResult(categoryVoList);
    }

}