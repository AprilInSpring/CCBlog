package com.zxnk.controller;

import com.zxnk.dto.RoutersVo;
import com.zxnk.entity.LoginUser;
import com.zxnk.entity.Menu;
import com.zxnk.entity.User;
import com.zxnk.service.AdminLoginService;
import com.zxnk.service.MenuService;
import com.zxnk.util.ResponseResult;
import com.zxnk.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * @return: com.zxnk.util.ResponseResult
     * @decription 获取登录用户的身份信息，包括权限、角色
     * @date 2023/5/9 10:48
    */
    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        //获取当前用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        return adminLoginService.getUserInfo(loginUser.getUser());
    }

    /**
     * @return: com.zxnk.util.ResponseResult
     * @decription 获取路由信息
     * @date 2023/5/9 10:49
    */
    @GetMapping("/getRouters")
    public ResponseResult getRouters(){
        Long userId = SecurityUtils.getUserId();
        //获取当前用户可用的路由树
        List<Menu> routerMenuTree = adminLoginService.getRouterMenuTreeByUserId(userId);
        //封装数据并返回
        return ResponseResult.okResult(new RoutersVo(routerMenuTree));
    }

    /**
     * @return: com.zxnk.util.ResponseResult
     * @decription 退出登录，并清除redis和SecurityContext中的身份信息
     * @date 2023/5/9 10:54
    */
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return adminLoginService.logout();
    }
}