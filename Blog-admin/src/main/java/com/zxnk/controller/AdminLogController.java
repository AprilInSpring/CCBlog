package com.zxnk.controller;

import com.zxnk.entity.LoginUser;
import com.zxnk.entity.User;
import com.zxnk.service.AdminLoginService;
import com.zxnk.util.ResponseResult;
import com.zxnk.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AdminLogController
 * @Description 管理员登录控制器
 * @Author cc
 * @Date 2023/5/8 9:56
 * @Version 1.0
 */
@RestController
public class AdminLogController {

    @Autowired
    private AdminLoginService adminLoginService;

    /**
     * @param user 管理员用户
     * @return: com.zxnk.util.ResponseResult
     * @decription 管理员用户完成登录
     * @date 2023/5/8 10:04
    */
    @PostMapping("/user/login")
    public ResponseResult adminLogin(@RequestBody User user){
        return adminLoginService.adminLogin(user);
    }

    @GetMapping("/cc")
    public String test(String cc){
        return cc;
    }

    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        //获取当前用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        return adminLoginService.getUserInfo(loginUser.getUser());
    }

}