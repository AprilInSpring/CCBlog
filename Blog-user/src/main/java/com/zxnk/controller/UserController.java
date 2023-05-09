package com.zxnk.controller;

import com.zxnk.annotation.SystemLog;
import com.zxnk.entity.User;
import com.zxnk.service.UserService;
import com.zxnk.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserController
 * @Description 前台用户控制类
 * @Author cc
 * @Date 2023/5/6 17:59
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Api(tags = "前台用户控制类",description = "前台用户相应接口")
public class UserController {

    @Autowired
    private UserService userService;

    //获取用户信息
    @GetMapping("/userInfo")
    @ApiOperation("查看用户个人信息")
    public ResponseResult getUserInfo(){
        return userService.getUserInfo();
    }

    @PutMapping("/userInfo")
    @ApiOperation("修改用户个人信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUser(user);
    }

    @PostMapping("/register")
    @ApiOperation(value = "进行新用户的注册",notes = "用名户、昵称不能重复,密码必须是小写英文字母加数字且长度大于6，而且各类数据均不可为空")
    @SystemLog(Description = "进行用户注册")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}