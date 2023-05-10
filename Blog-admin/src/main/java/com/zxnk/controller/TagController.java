package com.zxnk.controller;

import com.zxnk.dto.TagListDto;
import com.zxnk.entity.Tag;
import com.zxnk.service.CategoryService;
import com.zxnk.service.TagService;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName TagController
 * @Description 标签控制类
 * @Author cc
 * @Date 2023/5/9 13:25
 * @Version 1.0
 */
@RestController
@RequestMapping("/content")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * @param pageNum   分页页码
     * @param pageSize  分页大小
     * @param tagListDto 前端分页条件数据封装类
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据分页条件和查询条件进行标签的查询
     * @date 2023/5/9 13:50
    */
    @GetMapping("/tag/list")
    public ResponseResult getTagList(@RequestParam(defaultValue = "1")Integer pageNum,
                                     @RequestParam(defaultValue = "10")Integer pageSize,
                                     TagListDto tagListDto){
        return tagService.selectPageByDto(pageNum,pageSize,tagListDto);
    }

    /**
     * @param tagListDto 前端分页条件数据封装类
     * @return: com.zxnk.util.ResponseResult
     * @decription 添加标签
     * @date 2023/5/9 13:51
    */
    @PostMapping("/tag")
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }

    /**
     * @param id 标签id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据标签id删除标签对象
     * @date 2023/5/9 16:09
    */
    @DeleteMapping("/tag/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.deleteTag(id);
    }

    /**
     * @param id 标签id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据标签id查询标签对象
     * @date 2023/5/9 16:28
    */
    @GetMapping("/tag/{id}")
    public ResponseResult findById(@PathVariable Long id){
        return tagService.findById(id);
    }

    /**
     * @param tag 标签对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据标签对象完成数据的更新
     * @date 2023/5/9 16:29
    */
    @PutMapping("/tag")
    public ResponseResult updateTag(@RequestBody Tag tag){
        return tagService.updateTag(tag);
    }

    @GetMapping("/tag/listAllTag")
    public ResponseResult getAllTag(){
        return tagService.findAll();
    }
}