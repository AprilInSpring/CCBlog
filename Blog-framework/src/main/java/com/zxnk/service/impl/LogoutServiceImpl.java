package com.zxnk.service.impl;

import com.zxnk.dao.LoginUser;
import com.zxnk.service.LogoutService;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @ClassName LogoutServiceImpl
 * @Description 推出登录接口实现类
 * @Author cc
 * @Date 2023/5/4 18:56
 * @Version 1.0
 */
@Service
public class LogoutServiceImpl implements LogoutService {

    //redis模板类
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public ResponseResult logout() {
        //1.清除redis缓存
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisTemplate.delete("loginUser:"+userId.toString());
        //清除认证状态
        SecurityContextHolder.clearContext();
        return ResponseResult.okResult();
    }
}