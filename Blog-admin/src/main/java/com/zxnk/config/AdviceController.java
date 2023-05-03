package com.zxnk.config;

import com.zxnk.exception.SystemException;
import com.zxnk.util.AppHttpCodeEnum;
import com.zxnk.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName AdviceController
 * @Description 全局异常处理器
 * @Author cc
 * @Date 2023/5/3 21:54
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class AdviceController{

    //系统自定义异常
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemException(SystemException exception){
        log.info("发生自定义异常:"+exception.getMsg());
        return ResponseResult.errorResult(exception.getCode(),exception.getMsg());
    }

    //常规异常捕获
    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception exception){
        log.info("发生自定义异常:"+exception.getMessage());
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR,exception.getMessage());
    }
}