package com.zxnk.service.impl;

import com.zxnk.dto.BlogUserLoginVo;
import com.zxnk.entity.LoginUser;
import com.zxnk.entity.User;
import com.zxnk.dto.UserInfoVo;
import com.zxnk.service.UserLoginService;
import com.zxnk.util.BeanCopyUtils;
import com.zxnk.util.JwtUtil;
import com.zxnk.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName BlogLoginServiceImpl
 * @Description 登录服务类
 * @Author cc
 * @Date 2023/5/3 11:08
 * @Version 1.0
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    //用户认证管理器
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public ResponseResult<BlogUserLoginVo> login(User user) {
        //登录权证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //认证信息，该方法会调用userDetailService的认证逻辑，进行判断，此方法会使用UserDetailService进行逻辑判断
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //获取认证用户信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //认证通过，生成token
        String userId = loginUser.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);
        //把用户id和用户信息存入redis，过期时间为10分钟
        redisTemplate.opsForValue().set("loginUser:"+userId,loginUser,10, TimeUnit.MINUTES);
        //把token和userInfo封装并返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(token, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }
}