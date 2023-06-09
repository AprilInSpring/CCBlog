package com.zxnk.controller;

import com.zxnk.annotation.SystemLog;
import com.zxnk.dto.BlogUserLoginVo;
import com.zxnk.entity.User;
import com.zxnk.exception.SystemException;
import com.zxnk.service.LogoutService;
import com.zxnk.service.UserLoginService;
import com.zxnk.util.AppHttpCodeEnum;
import com.zxnk.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BlogLoginController
 * @Description 前台用户登录处理器
 * @Author cc
 * @Date 2023/5/3 11:05
 * @Version 1.0
 */
@RestController
@Api(tags = "前台用户登录控制类",description = "前台用户登录相应接口")
public class UserLogController {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private LogoutService logoutService;

    /**
     * @param user 用户对象
     * @return: com.zxnk.util.ResponseResult<com.zxnk.entity.BlogUserLoginVo>
     * @decription 根据前端传入的登录用户信息，完成user对象的封装，并进行相应的逻辑处理，完成登录认证
     * @date 2023/5/3 11:56
    */
    @PostMapping("/login")
    @SystemLog(Description = "用户登录")
    @ApiOperation("根据前端传入的登录用户信息，完成user对象的封装，并进行相应的逻辑处理，完成登录认证")
    public ResponseResult<BlogUserLoginVo> login(@RequestBody User user){
        //进行参数校验
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return userLoginService.login(user);
    }

    /**
     * @return: com.zxnk.util.ResponseResult
     * @decription 实现用户的退出登录功能，并删除redis缓存和清除认证状态
     * @date 2023/5/4 19:04
    */
    @PostMapping("/logout")
    @ApiOperation("实现用户的退出登录功能，并删除redis缓存和清除认证状态")
    public ResponseResult logout(){
        return logoutService.logout();
    }
}