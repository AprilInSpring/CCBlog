package com.zxnk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName BlogAdminApplication
 * @Description TODO
 * @Author cc
 * @Date 2023/4/25 10:45
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.zxnk.mapper")
@EnableAspectJAutoProxy
//开启定时任务
@EnableScheduling
//开启swagger2接口文档
@EnableSwagger2
public class BlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogAdminApplication.class,args);
        System.out.println("博客前台项目启动成功");
    }
}