package com.zxnk.aspect;

import com.alibaba.fastjson.JSON;
import com.zxnk.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName LogAspect
 * @Description 日志功能工具类
 * @Author cc
 * @Date 2023/5/7 11:29
 * @Version 1.0
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    /*{
        System.out.println("对象创建成功");
    }*/

    //切点，使用了该注解的方法都会被代理
    @Pointcut("@annotation(com.zxnk.annotation.SystemLog)")
    public void pointCut(){}

    //配置环绕通知
    @Around("pointCut()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        try {
            //前置通知
            beforeAdvice(proceedingJoinPoint);
            //方法执行，此代码一定要执行，否则被代理的方法不会执行，没有返回值
            result = proceedingJoinPoint.proceed();
            //后置通知
            afterAdvice(result);
        }finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }
        return result;
    }

    //前置打印消息
    private void beforeAdvice(ProceedingJoinPoint proceedingJoinPoint){
        //获取请求对象,带有contextHolder的对象都是上下文对象，javaWeb基于面向对象的理念，把请求也封装成上下文对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获取注解对象
        SystemLog systemLog = getSystemLog(proceedingJoinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("Description   : {}",systemLog.Description());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",proceedingJoinPoint.getTarget().getClass().getName(),proceedingJoinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSON(proceedingJoinPoint.getArgs()));
    }

    //后置打印消息
    private void afterAdvice(Object result){
        // 打印出参
        log.info("Response       : {}",JSON.toJSONString(result));
    }

    //获取注解对象
    private SystemLog getSystemLog(ProceedingJoinPoint proceedingJoinPoint){
        //获取具体的代理方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //通过方法获取注解
        SystemLog systemLog = signature.getMethod().getAnnotation(SystemLog.class);
        return systemLog;
    }
}