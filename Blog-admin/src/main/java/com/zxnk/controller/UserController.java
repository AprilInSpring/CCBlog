package com.zxnk.controller;

import com.zxnk.entity.User;
import com.zxnk.service.UserService;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.event.PaintEvent;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author cc
 * @Date 2023/5/10 16:47
 * @Version 1.0
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @param pageNum       分页页码
     * @param pageSize      分页大小
     * @param userName      用户名
     * @param phonenumber   手机号码
     * @param status 状态
     * @return: com.zxnk.util.ResponseResult
     * @decription
     * @date 2023/5/10 16:51
    */
    @GetMapping("/list")
    public ResponseResult selectAll(@RequestParam(defaultValue = "1") Integer pageNum,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    String userName,String phonenumber,String status){
    return userService.selectAll(pageNum,pageSize,userName,phonenumber,status);
    }

    /**
     * @param user 用户对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 完成用户对象的新增
     * @date 2023/5/10 16:59
    */
    @PostMapping()
    public ResponseResult addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
     * @param id 用户id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据用户id逻辑删除用户
     * @date 2023/5/10 17:15
    */
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id){
        return userService.deleteUserById(id);
    }

    /**
     * @param id 用户id
     * @return: com.zxnk.util.ResponseResult
     * @decription 根据用户id查询相关数据（用户，全部角色，用户角色）
     * @date 2023/5/10 18:13
    */
    @GetMapping("/{id}")
    public ResponseResult getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    /**
     * @param user 用户对象
     * @return: com.zxnk.util.ResponseResult
     * @decription 完成用户修改
     * @date 2023/5/10 18:24
    */
    @PutMapping()
    public ResponseResult updateUserById(@RequestBody User user){
        return userService.updateUserById(user);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody User user){
        return userService.changeStatus(user);
    }
}