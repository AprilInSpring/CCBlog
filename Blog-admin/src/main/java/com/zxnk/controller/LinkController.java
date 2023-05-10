package com.zxnk.controller;

import com.zxnk.entity.Link;
import com.zxnk.service.LinkService;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName LinkController
 * @Description TODO
 * @Author cc
 * @Date 2023/5/10 19:10
 * @Version 1.0
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * @param pageNum 分页页码
     * @param pageSize 分页大小
     * @param name 名字
     * @param status 状态
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据分页数据和查询数据对友链进行分页查询
     * @date 2023/5/10 19:12
    */
    @GetMapping("/list")
    public ResponseResult getAll(@RequestParam(defaultValue = "1")Integer pageNum,
                                 @RequestParam(defaultValue = "10")Integer pageSize,
                                 String name,String status){
        return linkService.getAll(pageNum,pageSize,name,status);
    }

    /**
     * @param link 友链对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 新增友链对象
     * @date 2023/5/10 19:18
    */
    @PostMapping()
    public ResponseResult addLink(@RequestBody Link link){
        return linkService.addLink(link);
    }

    /**
     * @param id 友链id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据id获取友链数据
     * @date 2023/5/10 19:19
    */
    @GetMapping("/{id}")
    public ResponseResult getById(@PathVariable Long id){
        return linkService.getById(id);
    }

    /**
     * @param link 友链数据
     * @return: com.zxnk.util.ResponseResult
     * @decription 更新友链数据
     * @date 2023/5/10 19:23
    */
    @PutMapping()
    public ResponseResult updateById(@RequestBody Link link){
        return linkService.updateById(link);
    }

    /**
     * @param id 友链id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据id逻辑删除友链数据
     * @date 2023/5/10 19:25
    */
    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return linkService.deleteById(id);
    }

    /**
     * @param link 友链对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 更改友链对象状态
     * @date 2023/5/10 19:31
    */
    @PutMapping("/changeLinkStatus")
    public ResponseResult changeStatus(@RequestBody Link link){
        return linkService.changeStatus(link);
    }
}