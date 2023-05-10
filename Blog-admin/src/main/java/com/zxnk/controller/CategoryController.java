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
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
     * @param pageNum 分页页码
     * @param pageSize 分页大小
     * @param name 名称
     * @param status 状态
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据分页数据和查询条件分页查询分类信息
     * @date 2023/5/10 18:58
    */
    @GetMapping("/list")
    public ResponseResult findAll(@RequestParam(defaultValue = "1") Integer pageNum,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  String name,String status){
        return categoryService.selectAll(pageNum,pageSize,name,status);
    }

    /**
     * @param category 分类对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 新增分类对象
     * @date 2023/5/10 18:59
    */
    @PostMapping()
    public ResponseResult addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }

    /**
     * @param id 分类id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据分类id查询分类对象
     * @date 2023/5/10 19:01
    */
    @GetMapping("/{id}")
    public ResponseResult getCategoryById(@PathVariable long id){
        return categoryService.getById(id);
    }

    /**
     * @param category 分类对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 更新分类对象
     * @date 2023/5/10 19:03
    */
    @PutMapping()
    public ResponseResult updateById(@RequestBody Category category){
        return categoryService.updateById(category);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable long id){
        return categoryService.deleteById(id);
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