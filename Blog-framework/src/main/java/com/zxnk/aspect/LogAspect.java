package com.zxnk.aspect;

import com.alibaba.fastjson.JSON;
import com.zxnk.annotation.CommonLog;
import com.zxnk.annotation.SystemLog;
import com.zxnk.entity.Audience;
import com.zxnk.entity.BrowseLog;
import com.zxnk.service.BrowseLogService;
import com.zxnk.util.CommonIpAddressUtil;
import com.zxnk.util.CommonUaUtil;
import com.zxnk.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BrowseLogService browseLogService;
    /*{
        System.out.println("对象创建成功");
    }*/

    //切点，使用了该注解的方法都会被代理
    @Pointcut("@annotation(com.zxnk.annotation.SystemLog)")
    public void pointCut(){}

    @Pointcut("@annotation(com.zxnk.annotation.ViewLog)")
    public void viewLog(){}

    //常规操作日志对象
    @Pointcut("@annotation(com.zxnk.annotation.CommonLog)")
    public void commonLog(){}

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

    //配置前置通知
    @Before("viewLog()")
    public void loginBeforeLog(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取请求ip
        String host = request.getRemoteHost();
        //获取归属地
        String addr = CommonIpAddressUtil.getCityInfo(host);
        //添加缓存数据
        redisTemplate.opsForList().rightPush("audience",Audience.builder().ip(host).address(addr).date(new Date()).build());
        //增加浏览数
        redisTemplate.opsForValue().increment("count");
        log.info(host+"ip,在"+ DateUtil.format(new Date()) +"访问了网站");
    }

    //常规操作日志通知
    @AfterReturning(pointcut = "commonLog()")
    public void doAfterReturning(JoinPoint joinPoint){
        //获取代理方法的注解对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CommonLog commonLog = method.getAnnotation(CommonLog.class);
        executeCommonLog(commonLog,joinPoint);
    }

    //前置打印消息
    private void beforeAdvice(ProceedingJoinPoint proceedingJoinPoint){
        //获取请求对象
        HttpServletRequest request = getRequest();
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

    //完成日志数据的插入
    private void executeCommonLog(CommonLog commonLog, JoinPoint joinPoint){
        BrowseLog browseLog = new BrowseLog();
        //获取请求对象
        HttpServletRequest request = getRequest();
        //ip&addr&browser&os
        String ip = CommonIpAddressUtil.getIp(request);
        String addr = CommonIpAddressUtil.getCityInfo(ip);
        String browser = CommonUaUtil.getBrowser(request);
        String os = CommonUaUtil.getOs(request);
        //logName&methodName&className&url
        String logName = commonLog.logName();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        String url = String.valueOf(request.getRequestURL());
        //完成数据封装，插入
        browseLog.setIp(ip);
        browseLog.setAddr(addr);
        browseLog.setBrowser(browser);
        browseLog.setOs(os);
        browseLog.setLogName(logName);
        browseLog.setMethodName(methodName);
        browseLog.setClassName(className);
        browseLog.setUrl(url);
        browseLog.setLocalTime(new Date());

        browseLogService.insert(browseLog);
    }

    private HttpServletRequest getRequest(){
        //获取请求对象,带有contextHolder的对象都是上下文对象，javaWeb基于面向对象的理念，把请求也封装成上下文对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }
}