package com.zxnk.filter;

import com.alibaba.fastjson.JSON;
import com.zxnk.dao.LoginUser;
import com.zxnk.util.AppHttpCodeEnum;
import com.zxnk.util.JwtUtil;
import com.zxnk.util.ResponseResult;
import com.zxnk.util.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @ClassName JwtAuthenticationTokenFilter
 * @Description jwt的身份认证过滤器
 * @Author cc
 * @Date 2023/5/3 16:45
 * @Version 1.0
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = httpServletRequest.getHeader("token");
        //没有token则需要进行登录
        if(!StringUtils.hasText(token)){
            System.out.println("没有token身份信息，直接放行");
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //解析token
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //token解析错误，重新登录
            System.out.println("token解析错误");
            e.printStackTrace();
            ResponseResult errorResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(errorResult));
            return;
        }
        //获取userId
        String userId = claims.getSubject();
        //从redis中获取用户信息
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get("loginUser:" + userId);
        if(Objects.isNull(loginUser)){
            System.out.println("redis身份信息过期，重新登录");
            ResponseResult errorResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(errorResult));
            return;
        }
        //将身份信息存入securityContext
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //请求跳转
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}