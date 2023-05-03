package com.zxnk.handler;

import com.alibaba.fastjson.JSON;
import com.zxnk.util.AppHttpCodeEnum;
import com.zxnk.util.ResponseResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthenticationEntryPoint
 * @Description 登录失败处理器
 * @Author cc
 * @Date 2023/5/3 21:41
 * @Version 1.0
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        ResponseResult responseResult = null;
        //判断是哪种异常，提示相应的报错信息
        if(e instanceof BadCredentialsException){
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(),e.getMessage());
        }else if(e instanceof InsufficientAuthenticationException){
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN.getCode(),e.getMessage());
        }else {
            responseResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"认证或授权失败");
        }
        //返回给前端显示
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(200);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(JSON.toJSONString(responseResult));
    }
}