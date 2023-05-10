package com.zxnk.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.zxnk.dto.ExcelCategoryVo;
import com.zxnk.entity.Category;
import com.zxnk.exception.SystemException;
import com.zxnk.service.CategoryService;
import com.zxnk.util.AppHttpCodeEnum;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.ResponseResult;
import com.zxnk.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @ClassName CategoryController
 * @Description TODO
 * @Author cc
 * @Date 2023/5/9 17:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @return: com.zxnk.util.ResponseResult
     * @decription 查询所有可用的博文分类
     * @date 2023/5/9 16:44
     */
    @GetMapping("/listAllCategory")
    public ResponseResult getAllCategories(){
        return categoryService.getAll();
    }

    /**
     * @param response 响应对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 调用该方法下载分类类别并进行excel表导出
     * @date 2023/5/9 21:03
    */
    //拥有该权限才可以调用此方法
    @PreAuthorize("@permissions.hasAnyAuthorities('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        try {
            //设置下载响应头信息
            WebUtils.setDownLoadHeader("CC博客项目文章分类表.xlsx",response);
            //获取所有可用的博文分类
            List<Category> allCategories = categoryService.getAllCategories();
            //完成属性赋值，简化对象的属性
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeans(allCategories, ExcelCategoryVo.class);
            //调用框架完成下载输出
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class)  //响应对象，数据类class属性
                    .autoCloseStream(Boolean.FALSE)              //不关闭流
                    .sheet("CC博文分类")                //excel顶部的命名
                    .doWrite(excelCategoryVos);                  //写出数据
        } catch (Exception e) {
            // 重置response，清除数据和响应参数
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            //写出错误信息
            response.getWriter().println(JSON.toJSONString(ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR)));
        }
    }
}